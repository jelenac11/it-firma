package tim13.bankms.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {

	private String merchantId;

	private String merchantPassword;

	private Long merchantOrderId;

	private Timestamp timestamp;

	private Double amount;

	private String successUrl;

	private String cancelUrl;

	private String errorUrl;

}
