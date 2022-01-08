package tim13.paypal.dto;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDto {

	private List<PaymentAttributeDTO> attributes;

	@NotNull(message = "Merchant order id is not provided.")
	@PositiveOrZero
	private Long merchantOrderId;

	@NotNull(message = "Amount is not provided.")
	@Min(value = 1,message = "Amount must be greater than 0")
	private Double amount;

	@NotBlank(message = "Success url is not provided.")
	@Pattern(regexp = "(http(s)?:\\/\\/)((localhost:))[(\\/)?a-zA-Z0-9@:%._\\+~#=-]{1,256}")
	private String successURL;

	@NotBlank(message = "Cancel url is not provided.")
	@Pattern(regexp = "(http(s)?:\\/\\/)((localhost:))[(\\/)?a-zA-Z0-9@:%._\\+~#=-]{1,256}")
	private String failedURL;

	@NotBlank(message = "Error url is not provided.")
	@Pattern(regexp = "(http(s)?:\\/\\/)((localhost:))[(\\/)?a-zA-Z0-9@:%._\\+~#=-]{1,256}")
	private String errorURL;

}
