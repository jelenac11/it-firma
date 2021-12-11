package tim13.paypal.mapper;

import org.springframework.stereotype.Component;

import tim13.paypal.dto.PaymentRequestDto;
import tim13.paypal.model.PaymentRequest;

@Component
public class PaymentRequestMapper {

	public PaymentRequest toEntity(PaymentRequestDto paymentRequestDto) {
		PaymentRequest paymentRequest = new PaymentRequest();

		paymentRequest.setClientId(paymentRequestDto.getClientId());
		paymentRequest.setClientSecret(paymentRequestDto.getClientSecret());
		paymentRequest.setAmount(paymentRequestDto.getAmount());
		paymentRequest.setSuccessUrl(paymentRequestDto.getSuccessUrl());
		paymentRequest.setCancelUrl(paymentRequestDto.getCancelUrl());
		paymentRequest.setErrorUrl(paymentRequestDto.getErrorUrl());

		return paymentRequest;
	}

	public PaymentRequestDto toDto(PaymentRequest paymentRequest) {
		PaymentRequestDto paymentRequestDto = new PaymentRequestDto();

		paymentRequestDto.setClientId(paymentRequest.getClientId());
		paymentRequestDto.setClientSecret(paymentRequest.getClientSecret());
		paymentRequestDto.setAmount(paymentRequest.getAmount());
		paymentRequestDto.setSuccessUrl(paymentRequest.getSuccessUrl());
		paymentRequestDto.setCancelUrl(paymentRequest.getCancelUrl());
		paymentRequestDto.setErrorUrl(paymentRequest.getErrorUrl());

		return paymentRequestDto;
	}
}
