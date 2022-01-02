package tim13.bitcoinservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCallbackDTO {

	private Long id;

	private String order_id;

	private String status;

	private String price_amount;

	private String price_currency;

	private String receive_currency;

	private String receive_amount;

	private String pay_amount;

	private String pay_currency;

	private String underpaid_amount;

	private String overpaid_amount;

	private boolean is_refundable;

	private String created_at;

	private String token;

	private String error_message;

	@Override
	public String toString() {
		return "PaymentCallbackDTO [id=" + id + ", order_id=" + order_id + ", status=" + status + ", price_amount="
				+ price_amount + ", price_currency=" + price_currency + ", receive_currency=" + receive_currency
				+ ", receive_amount=" + receive_amount + ", pay_amount=" + pay_amount + ", pay_currency=" + pay_currency
				+ ", underpaid_amount=" + underpaid_amount + ", overpaid_amount=" + overpaid_amount + ", is_refundable="
				+ is_refundable + ", created_at=" + created_at + ", token=" + token + ", error_message=" + error_message
				+ "]";
	}
}
