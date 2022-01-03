package tim13.webshop.shop.dto;

import lombok.Setter;

import java.util.Date;

import lombok.Getter;

@Getter
@Setter
public class SubscriptionDTO {

	private Long id;

	private PlanDTO plan;

	private Date startDate;
	
}
