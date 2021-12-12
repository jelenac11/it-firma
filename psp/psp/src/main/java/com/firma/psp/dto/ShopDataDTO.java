package com.firma.psp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopDataDTO {

	@Min(0)
	private Long transactionId;
	@NotBlank
	private String merchant;
	@Min(1)
	private double totalPrice;
}
