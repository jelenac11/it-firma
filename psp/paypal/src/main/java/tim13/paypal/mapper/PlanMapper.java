package tim13.paypal.mapper;

import org.springframework.stereotype.Component;

import tim13.paypal.dto.PlanDto;
import tim13.paypal.model.Plan;

@Component
public class PlanMapper {

	public Plan toEntity(PlanDto dto) {
		Plan plan = new Plan();

		plan.setName(dto.getName());
		plan.setDescription(dto.getDescription());
		plan.setClientId(dto.getClientId());
		plan.setClientSecret(dto.getClientSecret());
		plan.setAmount(dto.getAmount());
		plan.setTypeOfPlan(dto.getTypeOfPlan());
		
		return plan;
	}

}
