package tim13.webshop.shop.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim13.webshop.shop.enums.TypeOfPlan;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanDTO {

	private Long id;

	@NotEmpty(message = "Name must not be empty.")
	private String name;

	private String description;

	@NotNull(message = "Course must not be null.")
	private CourseDTO course;

	@NotNull(message = "Type of plan must not be empty.")
	private TypeOfPlan typeOfPlan;

	@NotNull(message = "Price must not be null.")
	@PositiveOrZero(message = "Price must be a positive number.")
	private Double price;

}
