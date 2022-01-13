package tim13.bankms.dto;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDataDTO {

	@NotNull(message = "Merchant id is not provided.")
	private Long merchantOrderId;

	@NotNull
	private Timestamp merchantTimestamp;

	@NotNull(message = "Amount is not provided.")
	@Min(value = 1,message = "Amount must be greater than 0")
	private Double amount;

	private List<PaymentAttributeDTO> attributes;

	@NotBlank(message = "Success url is not provided.")
	@Pattern(regexp = "(http(s)?:\\/\\/)((.*))[(\\/)?a-zA-Z0-9@:%._\\+~#=-]{1,256}")
	private String successURL;

	@NotBlank(message = "Error url is not provided.")
	@Pattern(regexp = "(http(s)?:\\/\\/)((.*))[(\\/)?a-zA-Z0-9@:%._\\+~#=-]{1,256}")
	private String errorURL;

	@NotBlank(message = "Failed url is not provided.")
	@Pattern(regexp = "(http(s)?:\\/\\/)((.*))[(\\/)?a-zA-Z0-9@:%._\\+~#=-]{1,256}")
	private String failedURL;
}
