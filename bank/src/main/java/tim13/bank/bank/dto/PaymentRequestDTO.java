package tim13.bank.bank.dto;

import java.sql.Timestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {

	@NotBlank(message = "Merchant Id is not provided.")
	private String merchantId;

	@NotBlank(message = "Merchant password is not provided.")
	private String merchantPassword;

	@NotNull(message = "Merchant order id is not provided.")
	@PositiveOrZero
	private Long merchantOrderId;

	@NotNull(message = "Merchant timestamp is not provided.")
	private Timestamp timestamp;

	@NotNull(message = "Amount is not provided.")
	@Min(value = 1,message = "Amount must be greater than 0")
	private Double amount;

	@NotBlank(message = "Success url is not provided.")
	@Pattern(regexp = "(http(s)?:\\/\\/)((localhost:))[(\\/)?a-zA-Z0-9@:%._\\+~#=-]{1,256}")
	private String successUrl;

	@NotBlank(message = "Cancel url is not provided.")
	@Pattern(regexp = "(http(s)?:\\/\\/)((localhost:))[(\\/)?a-zA-Z0-9@:%._\\+~#=-]{1,256}")
	private String cancelUrl;

	@NotBlank(message = "Error url is not provided.")
	@Pattern(regexp = "(http(s)?:\\/\\/)((localhost:))[(\\/)?a-zA-Z0-9@:%._\\+~#=-]{1,256}")
	private String errorUrl;

}
