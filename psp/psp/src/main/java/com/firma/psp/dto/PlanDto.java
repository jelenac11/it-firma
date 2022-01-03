package com.firma.psp.dto;

import com.firma.psp.enums.TypeOfPlan;

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
	
	private TypeOfPlan typeOfPlan;
	
}
