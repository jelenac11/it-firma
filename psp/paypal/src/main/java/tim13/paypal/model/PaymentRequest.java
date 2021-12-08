package tim13.paypal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnTransformer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PaymentRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@ColumnTransformer(forColumn = "client_id", read = "pgp_sym_decrypt(client_id, current_setting('encrypt.key'), 'cipher-algo=aes256')", write = "pgp_sym_encrypt(?, current_setting('encrypt.key'), 'cipher-algo=aes256')")
	private String clientId;

	@Column(nullable = false)
	@ColumnTransformer(forColumn = "client_secret", read = "pgp_sym_decrypt(client_secret, current_setting('encrypt.key'), 'cipher-algo=aes256')", write = "pgp_sym_encrypt(?, current_setting('encrypt.key'), 'cipher-algo=aes256')")
	private String clientSecret;

	@Column(nullable = false)
	private String paymentId;

	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false)
	private String successUrl;

	@Column(nullable = false)
	private String cancelUrl;

	@Column(nullable = false)
	private String errorUrl;
}
