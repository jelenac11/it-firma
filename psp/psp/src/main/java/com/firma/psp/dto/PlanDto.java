package com.firma.psp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanDto {

	private String name;

	private String description;
	
	private String merchantEmail;

	private String clientId;

	private String clientSecret;

	private Double amount;

	private ProductDto product;
	
}
