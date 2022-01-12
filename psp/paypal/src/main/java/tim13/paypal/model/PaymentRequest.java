package tim13.paypal.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import tim13.paypal.converter.CryptoStringConverter;

@Getter
@Setter
@Entity
public class PaymentRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@Convert(converter = CryptoStringConverter.class)
	private String clientId;

	@Column(nullable = false)
	@Convert(converter = CryptoStringConverter.class)
	private String clientSecret;

	@Column(nullable = false)
	private String paymentId;

	@Column(nullable = false)
	private Long merchantOrderId;

	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false)
	private String successUrl;

	@Column(nullable = false)
	private String cancelUrl;

	@Column(nullable = false)
	private String errorUrl;
}
