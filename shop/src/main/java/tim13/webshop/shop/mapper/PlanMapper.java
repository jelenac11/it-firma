package tim13.webshop.shop.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tim13.webshop.shop.dto.PlanDTO;
import tim13.webshop.shop.model.Plan;

@Component
public class PlanMapper {

	@Autowired
	private CourseMapper courseMapper;

	public PlanDTO toDTO(Plan plan) {
		PlanDTO planDTO = new PlanDTO();

		planDTO.setId(plan.getId());
		planDTO.setName(plan.getName());
		planDTO.setDescription(plan.getDescription());
		planDTO.setCourse(courseMapper.toDTO(plan.getCourse()));
		planDTO.setPrice(plan.getPrice());
		planDTO.setTypeOfPlan(plan.getTypeOfPlan());

		return planDTO;
	}

	public Plan toEntity(PlanDTO planDTO) {
		Plan plan = new Plan();

		plan.setId(planDTO.getId());
		plan.setName(planDTO.getName());
		plan.setDescription(planDTO.getDescription());
		plan.setCourse(courseMapper.toEntity(planDTO.getCourse()));
		plan.setPrice(planDTO.getPrice());
		plan.setTypeOfPlan(planDTO.getTypeOfPlan());

		return plan;
	}
}
