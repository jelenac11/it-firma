package com.firma.psp.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChosenPaymentMethodDTO {

	@NotEmpty
	private Long id;
	@NotNull
	private List<PaymentAttributeValueDTO> values;
}
