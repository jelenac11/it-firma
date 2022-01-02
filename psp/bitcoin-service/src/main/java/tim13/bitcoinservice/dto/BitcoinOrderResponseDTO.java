package tim13.bitcoinservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BitcoinOrderResponseDTO {

	private Long id;
	private String status;
	private String price_currency;
	private String price_amount;
	private String receive_currency;
	private String receive_amount;
	private String created_at;
	private String order_id;
	private String payment_url;
	private String token;

}
