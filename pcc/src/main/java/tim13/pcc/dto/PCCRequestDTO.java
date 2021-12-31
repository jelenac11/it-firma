package tim13.pcc.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PCCRequestDTO {

	@NotNull(message = "Acquirer order id is not provided.")
	private Long acquirerOrderId;

	@NotNull(message = "Acquirer timestamp is not provided.")
	private Timestamp acquirerTimestamp;

	@NotNull(message = "Amount is not provided.")
	private Double amount;

	@NotNull(message = "Pan is not provided.")
	private String PAN;

	@NotNull(message = "Card holder name is not provided.")
	private String cardHolderName;

	@NotNull(message = "Expiration date is not provided.")
	private String expirationDate;

	@NotNull(message = "Security code is not provided.")
	private String securityCode;

}
