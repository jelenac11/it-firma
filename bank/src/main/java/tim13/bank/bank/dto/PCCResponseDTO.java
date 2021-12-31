package tim13.bank.bank.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim13.bank.bank.model.TransactionStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PCCResponseDTO {

	@NotNull(message = "Acquirer order id is not provided.")
	private Long acquirerOrderId;

	@NotNull(message = "Issuer order id is not provided.")
	private Long issuerOrderId;

	@NotNull(message = "Acquirer timestamp is not provided.")
	private Timestamp acquirerTimestamp;

	@NotNull(message = "Issuer timestamp is not provided.")
	private Timestamp issuerTimestamp;

	@NotNull(message = "Status is not provided.")
	private TransactionStatus status;

}
