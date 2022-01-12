package tim13.bank.bank.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantDTO {

	@NotBlank(message = "Name must not be empty")
	private String name;

	@Email(message = "Email not valid")
	private String email;

	@NotBlank(message = "Currency must not be empty")
	@Pattern(regexp = "EUR|RSD|USD|GBP", flags = Pattern.Flag.CASE_INSENSITIVE)
	private String currency;

	@Min(0)
	private Double balance;

	@NotBlank(message = "PAN number is empty.")
	@Pattern(regexp = "^[0-9]{16}$")
	private String pan;

}
