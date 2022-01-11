package com.firma.psp.services;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.firma.psp.dto.CancellingSubscriptionDto;
import com.firma.psp.dto.PlanDto;
import com.firma.psp.dto.SubscribeDto;
import com.firma.psp.exceptions.BaseException;
import com.firma.psp.model.Merchant;
import com.firma.psp.model.PaymentData;
import com.firma.psp.model.PaymentMethod;
import com.firma.psp.model.PaymentMethodAttribute;
import com.firma.psp.repositories.IMerchantRepository;
import com.firma.psp.repositories.IPaymentMethodRepository;

@Service
public class SubscriptionService {

	@Autowired
	private IMerchantRepository merchantRepository;

	@Autowired
	private IPaymentMethodRepository paymentMethodRepository;

	private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

	public String createPlan(PlanDto planDto) throws BaseException {
		logger.info("Reading merchant from database.");

		Merchant merchant = merchantRepository.findByEmail(planDto.getMerchantEmail());

		if (merchant == null) {
			logger.debug(String.format("Merchant with email %s doesn't exist.", planDto.getMerchantEmail()));

			throw new BaseException(HttpStatus.NOT_FOUND,
					String.format("Merchant with email %s doesn't exist.", planDto.getMerchantEmail()));
		}

		Optional<PaymentMethod> paymentMethod = merchant.getPaymentMethods().stream()
				.filter(method -> method.getName().toLowerCase().equals("paypal")).findFirst();

		if (!paymentMethod.isPresent()) {
			logger.debug("Merchant is not subscribed on payment with paypal.");

			throw new BaseException(HttpStatus.NOT_FOUND, "Merchant is not subscribed on payment with paypal.");
		}

		PaymentMethod paypalPaymentMethod = paymentMethod.get();

		PaymentData merchantPaypalId = getPaypalId(merchant, paypalPaymentMethod, merchant.getData());
		PaymentData merchantPaypalSecret = getPaypalSecret(merchant, paypalPaymentMethod, merchant.getData());

		if (merchantPaypalId == null || merchantPaypalSecret == null) {
			logger.debug("Credential for paypal doesn't exist.");

			throw new BaseException(HttpStatus.NOT_FOUND, "Credential for paypal doesn't exist.");
		}

		planDto.setClientId(merchantPaypalId.getValue());
		planDto.setClientSecret(merchantPaypalSecret.getValue());

		RestTemplate rs = new RestTemplate();

		try {
			logger.info("Sending request for creating plan on paypal.");

			return rs.postForEntity(paypalPaymentMethod.getUri() + "/api/subscription", planDto, String.class)
					.getBody();
		} catch (HttpStatusCodeException e) {
			logger.debug(e.getResponseBodyAsString());

			throw new BaseException(HttpStatus.resolve(e.getRawStatusCode()), e.getResponseBodyAsString());
		}
	}

	public String subscribe(SubscribeDto subscribeDto) throws BaseException {
		logger.info("Reading payment method from database.");

		PaymentMethod paypalPaymentMethod = paymentMethodRepository.findByName("Paypal");

		if (paypalPaymentMethod == null) {
			logger.debug("Payment with paypal is not supported.");

			throw new BaseException(HttpStatus.NOT_FOUND, "Payment with paypal is not supported.");
		}

		subscribeDto.setSuccessUrl(expandUrlWithId(subscribeDto.getSuccessUrl(), subscribeDto.getTransactionId()));
		subscribeDto.setCancelUrl(expandUrlWithId(subscribeDto.getCancelUrl(), subscribeDto.getTransactionId()));

		RestTemplate rs = new RestTemplate();

		try {
			logger.info("Sending request to subscribe user on plan with paypal.");

			return rs.postForEntity(paypalPaymentMethod.getUri() + "/api/subscription/subscribe", subscribeDto,
					String.class).getBody();
		} catch (HttpStatusCodeException e) {
			logger.debug(e.getResponseBodyAsString());

			throw new BaseException(HttpStatus.resolve(e.getRawStatusCode()), e.getResponseBodyAsString());
		}
	}

	public void unsubscribe(String subscriptionId, CancellingSubscriptionDto dto) throws BaseException {
		logger.info("Reading payment method from database.");

		PaymentMethod paypalPaymentMethod = paymentMethodRepository.findByName("Paypal");

		if (paypalPaymentMethod == null) {
			logger.debug("Payment with paypal is not supported.");

			throw new BaseException(HttpStatus.NOT_FOUND, "Payment with paypal is not supported.");
		}

		RestTemplate rs = new RestTemplate();

		try {
			logger.info("Sending request to cancel subscription for user on paypal.");

			rs.postForEntity(
					paypalPaymentMethod.getUri() + String.format("/api/subscription/unsubscribe/%s", subscriptionId),
					dto, Void.class);
		} catch (HttpStatusCodeException e) {
			logger.debug(e.getResponseBodyAsString());

			throw new BaseException(HttpStatus.resolve(e.getRawStatusCode()), e.getResponseBodyAsString());
		}
	}

	private PaymentData getPaypalId(Merchant merchant, PaymentMethod paymentMethod, Set<PaymentData> paymentData)
			throws BaseException {
		PaymentMethodAttribute methodAttribute = paymentMethod.getAttributes().stream()
				.filter(attribute -> attribute.getName().equals("Merchant client Id")).findFirst().orElseGet(null);

		if (methodAttribute == null) {
			logger.debug("Credentials for payment method doesn't exist.");

			throw new BaseException(HttpStatus.NOT_FOUND, "Credentials for payment method doesn't exist.");
		}

		return getPaymentDataByMerchantAndMethodAttribute(merchant, methodAttribute, paymentData);
	}

	private PaymentData getPaypalSecret(Merchant merchant, PaymentMethod paymentMethod, Set<PaymentData> paymentData)
			throws BaseException {
		PaymentMethodAttribute methodAttribute = getPaymentMethodAttribute(paymentMethod, "Merchant Client Secret");

		if (methodAttribute == null) {
			logger.debug("Credentials for payment method doesn't exist.");

			throw new BaseException(HttpStatus.NOT_FOUND, "Credentials for payment method doesn't exist.");
		}

		return getPaymentDataByMerchantAndMethodAttribute(merchant, methodAttribute, paymentData);
	}

	private PaymentMethodAttribute getPaymentMethodAttribute(PaymentMethod paymentMethod, String attributeName) {
		return paymentMethod.getAttributes().stream().filter(attribute -> attribute.getName().equals(attributeName))
				.findFirst().orElseGet(null);
	}

	private PaymentData getPaymentDataByMerchantAndMethodAttribute(Merchant merchant,
			PaymentMethodAttribute methodAttribute, Set<PaymentData> paymentData) {
		return paymentData.stream().filter(data -> data.getAttribute().getId() == methodAttribute.getId()
				&& data.getMerchant().getId() == merchant.getId()).findFirst().orElseGet(null);
	}

	private String expandUrlWithId(String url, Long id) {
		return url + "/" + id;
	}
}
