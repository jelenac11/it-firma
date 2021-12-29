package com.firma.psp.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.firma.psp.dto.CancellingSubscriptionDto;
import com.firma.psp.dto.PlanDto;
import com.firma.psp.dto.SubscribeDto;
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

	public String createPlan(PlanDto planDto) {
		Merchant merchant = merchantRepository.findByEmail(planDto.getMerchantEmail());
		PaymentMethod paypalPaymentMethod = merchant.getPaymentMethods().stream()
				.filter(method -> method.getName().toLowerCase().equals("paypal")).findFirst().orElseGet(null);

		PaymentData merchantPaypalId = getPaypalId(merchant, paypalPaymentMethod, merchant.getData());
		PaymentData merchantPaypalSecret = getPaypalSecret(merchant, paypalPaymentMethod, merchant.getData());

		planDto.setClientId(merchantPaypalId.getValue());
		planDto.setClientSecret(merchantPaypalSecret.getValue());

		RestTemplate rs = new RestTemplate();
		return rs.postForEntity(paypalPaymentMethod.getUri() + "/api/subscription", planDto, String.class).getBody();
	}

	public String subscribe(SubscribeDto subscribeDto) {
		PaymentMethod paypalPaymentMethod = paymentMethodRepository.findByName("Paypal");

		RestTemplate rs = new RestTemplate();
		return rs
				.postForEntity(paypalPaymentMethod.getUri() + "/api/subscription/subscribe", subscribeDto, String.class)
				.getBody();
	}

	public void unsubscribe(String subscriptionId, CancellingSubscriptionDto dto) throws Exception {
		PaymentMethod paypalPaymentMethod = paymentMethodRepository.findByName("Paypal");

		RestTemplate rs = new RestTemplate();

		rs.postForEntity(
				paypalPaymentMethod.getUri() + String.format("/api/subscription/unsubscribe/%s", subscriptionId), dto,
				Void.class);
	}

	private PaymentData getPaypalId(Merchant merchant, PaymentMethod paymentMethod, Set<PaymentData> paymentData) {
		PaymentMethodAttribute methodAttribute = paymentMethod.getAttributes().stream()
				.filter(attribute -> attribute.getName().equals("Merchant client Id")).findFirst().orElseGet(null);

		return getPaymentDataByMerchantAndMethodAttribute(merchant, methodAttribute, paymentData);
	}

	private PaymentData getPaypalSecret(Merchant merchant, PaymentMethod paymentMethod, Set<PaymentData> paymentData) {
		PaymentMethodAttribute methodAttribute = getPaymentMethodAttribute(paymentMethod, "Merchant Client Secret");

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
}
