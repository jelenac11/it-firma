package com.firma.psp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribeDto {

	String planId;

	String successUrl;

	String errorUrl;

	String cancelUrl;

}
