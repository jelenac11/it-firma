package com.firma.psp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserTokenStateDTO {

	private String accessToken;
	private boolean setSupportedPaymentMethods;
	private Long expiresIn;

}
