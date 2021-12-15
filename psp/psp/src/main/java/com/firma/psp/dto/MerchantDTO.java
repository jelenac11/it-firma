package com.firma.psp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDTO {

	@NotEmpty
	private String email;
	@NotEmpty
	private String shopName;
	@NotEmpty
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[_#?!@$%^&*-.,:;]).{8,20}$", message = "Password must be between 8 and 20 characters long and must contain a number, a special character, a lowercase and an uppercase letter.")
	private String password;

}
