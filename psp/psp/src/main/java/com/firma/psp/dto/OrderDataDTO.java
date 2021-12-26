package com.firma.psp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.firma.psp.model.OrderData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDataDTO {

	@NotNull
	private Long transactionId;
	@Min(0)
	private double totalPrice;
	@NotEmpty
	private String merchantEmail;
	@NotNull
	private Long timestamp;
	@NotEmpty
	private String successUrl;
	@NotEmpty
	private String failUrl;
	@NotEmpty
	private String errorUrl;

	public OrderDataDTO(OrderData o) {
		this.transactionId = o.getTransactionId();
		this.totalPrice = o.getTotalPrice();
		this.merchantEmail = o.getMerchantEmail();
		this.successUrl = o.getSuccessUrl();
		this.failUrl = o.getFailUrl();
		this.errorUrl = o.getErrorUrl();
	}

}
