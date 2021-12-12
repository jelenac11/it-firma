package com.firma.psp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDTO {

	protected String email;
	protected String role;
	protected boolean setSupportedPaymentMethods;

}
