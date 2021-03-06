package tim13.paypal.mapper;

import org.springframework.stereotype.Component;

import tim13.paypal.dto.PaymentRequestDto;
import tim13.paypal.model.PaymentRequest;

@Component
public class PaymentRequestMapper {

	public PaymentRequest toEntity(PaymentRequestDto paymentRequestDto) {
		PaymentRequest paymentRequest = new PaymentRequest();

		paymentRequest.setClientId(paymentRequestDto.getAttributes().stream()
				.filter(method -> method.getName().equals("Merchant client Id")).findFirst().get().getValue());
		paymentRequest.setClientSecret(paymentRequestDto.getAttributes().stream()
				.filter(method -> method.getName().equals("Merchant Client Secret")).findFirst().get().getValue());
		paymentRequest.setMerchantOrderId(paymentRequestDto.getMerchantOrderId());
		paymentRequest.setAmount(paymentRequestDto.getAmount());
		paymentRequest.setSuccessUrl(paymentRequestDto.getSuccessURL());
		paymentRequest.setCancelUrl(paymentRequestDto.getFailedURL());
		paymentRequest.setErrorUrl(paymentRequestDto.getErrorURL());

		return paymentRequest;
	}

	/*
	 * public PaymentRequestDto toDto(PaymentRequest paymentRequest) {
	 * PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
	 * 
	 * paymentRequestDto.setClientId(paymentRequest.getClientId());
	 * paymentRequestDto.setClientSecret(paymentRequest.getClientSecret());
	 * paymentRequestDto.setMerchantOrderId(paymentRequest.getMerchantOrderId());
	 * paymentRequestDto.setAmount(paymentRequest.getAmount());
	 * paymentRequestDto.setSuccessUrl(paymentRequest.getSuccessUrl());
	 * paymentRequestDto.setCancelUrl(paymentRequest.getCancelUrl());
	 * paymentRequestDto.setErrorUrl(paymentRequest.getErrorUrl());
	 * 
	 * return paymentRequestDto; }
	 */
}
