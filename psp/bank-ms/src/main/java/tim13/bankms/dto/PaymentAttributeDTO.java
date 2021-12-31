package tim13.bankms.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAttributeDTO {

	@NotBlank(message = "Name is not provided.")
	private String name;

	@NotBlank(message = "Value is not provided.")
	private String value;
}
