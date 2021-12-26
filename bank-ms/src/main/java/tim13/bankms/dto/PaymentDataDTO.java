package tim13.bankms.dto;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDataDTO {

	@NotBlank(message = "Merchant id is not provided.")
	private Long merchantOrderId;

	@NotBlank(message = "Merchant timestamp is not provided.")
	private Timestamp merchantTimestamp;

	@NotBlank(message = "Amount is not provided.")
	private Double amount;

	private List<PaymentAttributeDTO> attributes;

	@NotBlank(message = "Success url is not provided.")
	private String successURL;

	@NotBlank(message = "Error url is not provided.")
	private String errorURL;

	@NotBlank(message = "Failed url is not provided.")
	private String failedURL;
}
