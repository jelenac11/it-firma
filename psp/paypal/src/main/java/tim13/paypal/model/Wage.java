package tim13.paypal.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wage {

	private Long merchantOrderId;
	
	@NotBlank(message = "Client id is not provided.")
	private String clientId;

	@NotBlank(message = "Client secret is not provided.")
	private String clientSecret;

	@NotBlank(message = "Receiver email is not provided.")
	private String receiver;

	@NotNull(message = "Amount is not provided.")
	@Min(value = 0, message = "Amount must be greater or equal to 0")
	private Double amount;

	@NotBlank(message = "Success url is not provided.")
	@Pattern(regexp = "(http(s)?:\\/\\/)((.*))[(\\/)?a-zA-Z0-9@:%._\\+~#=-]{1,256}")
	private String successURL;

	@NotBlank(message = "Cancel url is not provided.")
	@Pattern(regexp = "(http(s)?:\\/\\/)((.*))[(\\/)?a-zA-Z0-9@:%._\\+~#=-]{1,256}")
	private String failedURL;

	@NotBlank(message = "Error url is not provided.")
	@Pattern(regexp = "(http(s)?:\\/\\/)((.*))[(\\/)?a-zA-Z0-9@:%._\\+~#=-]{1,256}")
	private String errorURL;

}
