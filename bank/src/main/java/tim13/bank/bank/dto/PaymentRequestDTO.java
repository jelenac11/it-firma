package tim13.bank.bank.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;

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

	@NotBlank(message = "Merchant order id is not provided.")
	private Long merchantOrderId;

	@NotBlank(message = "Merchant timestamp is not provided.")
	private Timestamp timestamp;

	@NotBlank(message = "Amount is not provided.")
	private Double amount;

	@NotBlank(message = "Success url is not provided.")
	private String successUrl;

	@NotBlank(message = "Cancel url is not provided.")
	private String cancelUrl;

	@NotBlank(message = "Error url is not provided.")
	private String errorUrl;

}
