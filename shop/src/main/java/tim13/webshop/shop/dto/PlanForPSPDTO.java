package tim13.webshop.shop.dto;

import lombok.Getter;
import lombok.Setter;
import tim13.webshop.shop.enums.TypeOfPlan;

@Getter
@Setter
public class PlanForPSPDTO {

	private String name;

	private String description;

	private String merchantEmail;

	private Double amount;

	private ProductDTO product;
	
	private TypeOfPlan typeOfPlan;
}
