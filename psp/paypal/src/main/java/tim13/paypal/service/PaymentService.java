package tim13.paypal.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

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
import tim13.paypal.enumeration.TransactionStatus;
import tim13.paypal.exceptions.BaseException;
import tim13.paypal.model.PaymentRequest;
import tim13.paypal.model.Wage;
import tim13.paypal.repository.PaymentRequestRepository;
import tim13.paypal.util.ICurrencyConverter;

@Service
public class PaymentService {

	@Autowired
	PaymentRequestRepository paymentRequestRepository;

	@Autowired
	ICurrencyConverter currencyConverter;

	@Autowired
	TransactionService transactionService;

	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

	public String createUrl(PaymentRequest paymentRequest) {
		APIContext apiContext = createApiContext(paymentRequest);

		Payment payment = createPayment(paymentRequest);

		try {
			Payment createdPayment = payment.create(apiContext);

			paymentRequest.setPaymentId(createdPayment.getId());

			paymentRequestRepository.save(paymentRequest);

			logger.info("URL created.");

			return getLink(createdPayment.getLinks());
		} catch (PayPalRESTException e) {
			logger.debug(e.getMessage());

			return null;
		}
	}

	public String executePayment(String payerId, String paymentId) throws BaseException {
		PaymentRequest paymentRequest = paymentRequestRepository.findOneByPaymentId(paymentId);

		if (paymentRequest == null) {
			logger.debug(String.format("Payment request with id %s doesn't exist.", paymentId));

			throw new BaseException(HttpStatus.NOT_FOUND,
					String.format("Payment request with id %s doesn't exist.", paymentId));
		}

		APIContext apiContext = new APIContext(paymentRequest.getClientId(), paymentRequest.getClientSecret(),
				PaypalConstants.MODE);

		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);

		Payment payment = new Payment();
		payment.setId(paymentId);

		try {
			Payment paidTransaction = payment.execute(apiContext, paymentExecution);

			tim13.paypal.model.Transaction transaction = createTransaction(payerId, paidTransaction, paymentRequest);

			transactionService.save(transaction);

			logger.info("Payment execution.");

			return expandUrlWithId(paymentRequest.getSuccessUrl(), paymentRequest.getMerchantOrderId());
		} catch (PayPalRESTException e) {
			logger.debug(e.getMessage());

			return expandUrlWithId(paymentRequest.getErrorUrl(), paymentRequest.getMerchantOrderId());
		}
	}

	private tim13.paypal.model.Transaction createTransaction(String payerId, Payment paidTransaction,
			PaymentRequest paymentRequest) {
		tim13.paypal.model.Transaction transaction = new tim13.paypal.model.Transaction();

		transaction.setPaymentId(paymentRequest.getPaymentId());
		transaction.setMerchantId(paidTransaction.getTransactions().get(0).getPayee().getMerchantId());
		transaction.setPayerId(payerId);
		transaction.setMerchantOrderId(paymentRequest.getMerchantOrderId());
		transaction.setAmount(paymentRequest.getAmount());
		transaction.setStatus(TransactionStatus.APPROVED);

		logger.info("Transaction creation.");

		return transaction;
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

		logger.info("Payment creation.");

		return payment;
	}

	private Payer createPayer() {
		Payer payer = new Payer();

		payer.setPaymentMethod(PaypalConstants.PAYMENT_METHOD);
		logger.info("Payer creation.");

		return payer;
	}

	private RedirectUrls createRedirectUrls(PaymentRequest paymentRequest) {
		RedirectUrls redirectUrls = new RedirectUrls();

		redirectUrls.setReturnUrl(PaypalConstants.SUCCESS_URL);
		redirectUrls.setCancelUrl(expandUrlWithId(paymentRequest.getCancelUrl(), paymentRequest.getMerchantOrderId()));

		logger.info("Redirect URLs creation.");
		return redirectUrls;
	}

	private List<Transaction> createTransactions(PaymentRequest paymentRequest) {
		List<Transaction> transactions = new ArrayList<Transaction>();

		transactions.add(createTransaction(paymentRequest));

		logger.info("Transaction creation.");
		return transactions;
	}

	private Transaction createTransaction(PaymentRequest paymentRequest) {
		Transaction transaction = new Transaction();

		transaction.setAmount(createAmount(paymentRequest));

		logger.info("Transaction creation.");
		return transaction;
	}

	private Amount createAmount(PaymentRequest paymentRequest) {
		Amount amount = new Amount();

		amount.setCurrency(PaypalConstants.CURRENCY);
		amount.setTotal(convertCurrency(paymentRequest.getAmount()).toString());

		logger.info("Amount creation.");

		return amount;
	}

	private Double convertCurrency(Double amount) {
		return currencyConverter.convert("RSD", PaypalConstants.CURRENCY, amount);
	}

	private String getLink(List<Links> urls) {
		return urls.stream().filter(link -> link.getRel().equalsIgnoreCase(PaypalConstants.LINK_REL)).findFirst()
				.orElse(null).getHref();
	}

	private String expandUrlWithId(String url, Long id) {
		return url + "/" + id;
	}

	public String payWage(Wage entity) {
		logger.trace("Paying wage started.");

		JSONObject wageObject = new JSONObject();

		JSONObject senderBatch = createSenderBatch();
		JSONArray items = createItems(entity);

		wageObject.put("sender_batch_header", senderBatch);
		wageObject.put("items", items);

		try {
			sendRequestForPayingWage(PaypalConstants.PAYING_WAGE, wageObject, entity.getClientId(),
					entity.getClientSecret());
		} catch (BaseException e) {
			return expandUrlWithId(entity.getErrorURL(), entity.getMerchantOrderId());
		}

		return expandUrlWithId(entity.getSuccessURL(), entity.getMerchantOrderId());
	}

	private JSONObject createSenderBatch() {
		JSONObject senderBatch = new JSONObject();

		senderBatch.put("sender_batch_id", UUID.randomUUID().toString());

		return senderBatch;
	}

	private JSONArray createItems(Wage wage) {
		JSONObject item = new JSONObject();

		item.put("recipient_type", "EMAIL");
		item.put("amount", createAmount(wage.getAmount()));
		item.put("sender_item_id", UUID.randomUUID().toString());
		item.put("receiver", wage.getReceiver());

		JSONArray items = new JSONArray();

		items.put(item);

		return items;
	}

	private JSONObject createAmount(Double amount) {
		JSONObject amountObject = new JSONObject();

		amountObject.put("value", amount);
		amountObject.put("currency", "USD");

		return amountObject;
	}

	private JSONObject sendRequestForPayingWage(String url, JSONObject data, String merchantId, String merchantSecret)
			throws BaseException {
		JSONObject response = sendRequest(url, data, merchantId, merchantSecret);

		logger.info("Request for paying wage sent");

		return response;
	}

	private JSONObject sendRequest(String url, JSONObject data, String merchantId, String merchantSecret)
			throws BaseException {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();

		headers.set("Authorization", createAuthorization(merchantId, merchantSecret));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<>(data.toString(), headers);

		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

			return response.getBody() != null ? new JSONObject(response.getBody()) : null;
		} catch (HttpStatusCodeException e) {
			logger.debug("Error in sending request to paypal");

			JSONObject response = new JSONObject(e.getResponseBodyAsString());

			throw new BaseException(HttpStatus.resolve(e.getRawStatusCode()), response.getString("message"));
		}
	}

	private String createAuthorization(String merchantId, String merchantSecret) {
		String auth = merchantId + ":" + merchantSecret;

		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));

		return "Basic " + new String(encodedAuth);
	}
}
