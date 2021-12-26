package tim13.webshop.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDataDTO {

	private Long transactionId;
	private double totalPrice;
	private String merchantEmail;
	private Long timeStamp;
	private String successUrl;
	private String failUrl;
	private String errorUrl;

}
