package com.firma.psp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaypalDataDTO {

	private String clientId;

	private String clientSecret;

	private Long merchantOrderId;

	private Double amount;

	private String successUrl;

	private String cancelUrl;

	private String errorUrl;
}
