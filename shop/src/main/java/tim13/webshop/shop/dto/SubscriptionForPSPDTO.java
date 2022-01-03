package tim13.webshop.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionForPSPDTO {

	private String planId;

	private Long transactionId;

	private String successUrl;

	private String cancelUrl;

	private String errorUrl;
}
