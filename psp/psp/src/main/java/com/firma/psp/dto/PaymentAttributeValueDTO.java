package com.firma.psp.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAttributeValueDTO {

	@NotEmpty
	private Long id;
	@NotEmpty
	private String value;
}
