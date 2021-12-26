package tim13.paypal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanDto {

	private String name;

	private String description;

	private String clientId;

	private String clientSecret;

	private Double amount;

	private String successUrl;

	private String cancelUrl;

	private String errorUrl;

	private ProductDto product;
}
