package tim13.bitcoinservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BitcoinOrderRequestDTO {

	private String order_id;
	private Double price_amount;
	private String price_currency;
	private String receive_currency;
	private String title;
	private String description;
	private String callback_url;
	private String cancel_url;
	private String success_url;
	private String token;
	private String purchaser_email;

}
