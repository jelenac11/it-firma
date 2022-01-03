package tim13.paypal.dto;

import lombok.Getter;
import lombok.Setter;
import tim13.paypal.enums.TypeOfPlan;

@Getter
@Setter
public class PlanDto {

	private String name;

	private String description;

	private String clientId;

	private String clientSecret;

	private Double amount;

	private ProductDto product;

	private TypeOfPlan typeOfPlan;

}
