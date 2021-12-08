package tim13.paypal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import tim13.paypal.common.PaypalConstants;
import tim13.paypal.model.PaymentRequest;
import tim13.paypal.repository.PaymentRequestRepository;
import tim13.paypal.util.ICurrencyConverter;

@Service
public class PaymentService {

	@Autowired
	PaymentRequestRepository paymentRequestRepository;

	@Autowired
	ICurrencyConverter currencyConverter;

	public String createUrl(PaymentRequest paymentRequest) {
		APIContext apiContext = createApiContext(paymentRequest);

		Payment payment = createPayment(paymentRequest);

		try {
			Payment createdPayment = payment.create(apiContext);

			paymentRequest.setPaymentId(createdPayment.getId());

			paymentRequestRepository.save(paymentRequest);

			return getLink(createdPayment.getLinks());
		} catch (PayPalRESTException e) {
			e.printStackTrace();

			return null;
		}
	}

	public String executePayment(String payerId, String paymentId) {
		PaymentRequest paymentRequest = paymentRequestRepository.findOneByPaymentId(paymentId);

		APIContext apiContext = new APIContext(paymentRequest.getClientId(), paymentRequest.getClientSecret(),
				PaypalConstants.MODE);

		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);

		Payment payment = new Payment();
		payment.setId(paymentId);

		try {
			payment.execute(apiContext, paymentExecution);

			return expandUrlWithId(paymentRequest.getSuccessUrl(), paymentId);
		} catch (PayPalRESTException e) {
			e.printStackTrace();

			return expandUrlWithId(paymentRequest.getErrorUrl(), paymentId);
		}
	}

	private APIContext createApiContext(PaymentRequest paymentRequest) {
		APIContext apiContext = new APIContext(paymentRequest.getClientId(), paymentRequest.getClientSecret(),
				PaypalConstants.MODE);

		return apiContext;
	}

	private Payment createPayment(PaymentRequest paymentRequest) {
		Payment payment = new Payment();

		payment.setPayer(createPayer());
		payment.setRedirectUrls(createRedirectUrls(paymentRequest));
		payment.setTransactions(createTransactions(paymentRequest));
		payment.setIntent(PaypalConstants.INTENT);

		return payment;
	}

	private Payer createPayer() {
		Payer payer = new Payer();

		payer.setPaymentMethod(PaypalConstants.PAYMENT_METHOD);

		return payer;
	}

	private RedirectUrls createRedirectUrls(PaymentRequest paymentRequest) {
		RedirectUrls redirectUrls = new RedirectUrls();

		redirectUrls.setReturnUrl(PaypalConstants.SUCCESS_URL);
		redirectUrls.setCancelUrl(paymentRequest.getCancelUrl());

		return redirectUrls;
	}

	private List<Transaction> createTransactions(PaymentRequest paymentRequest) {
		List<Transaction> transactions = new ArrayList<Transaction>();

		transactions.add(createTransaction(paymentRequest));

		return transactions;
	}

	private Transaction createTransaction(PaymentRequest paymentRequest) {
		Transaction transaction = new Transaction();

		transaction.setAmount(createAmount(paymentRequest));

		return transaction;
	}

	private Amount createAmount(PaymentRequest paymentRequest) {
		Amount amount = new Amount();

		amount.setCurrency(PaypalConstants.CURRENCY);
		amount.setTotal(convertCurrency(paymentRequest.getAmount()).toString());

		return amount;
	}

	private Double convertCurrency(Double amount) {
		return currencyConverter.convert("RSD", PaypalConstants.CURRENCY, amount);
	}

	private String getLink(List<Links> urls) {
		return urls.stream().filter(link -> link.getRel().equalsIgnoreCase(PaypalConstants.LINK_REL)).findFirst()
				.orElse(null).getHref();
	}

	private String expandUrlWithId(String url, String id) {
		return url + "?service=paypal&id=" + id;
	}
}
