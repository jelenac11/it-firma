package tim13.bank.bank.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PCCRequestDTO {

	private Long acquirerOrderId;

	private Timestamp acquirerTimestamp;

	private Double amount;

	private String PAN;

	private String cardHolderName;

	private String expirationDate;

	private String securityCode;

}
