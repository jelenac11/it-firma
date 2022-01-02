package tim13.pcc.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim13.pcc.model.TransactionStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PCCResponseDTO {

	private Long acquirerOrderId;

	private Long issuerOrderId;

	private Timestamp acquirerTimestamp;

	private Timestamp issuerTimestamp;

	private TransactionStatus status;

}
