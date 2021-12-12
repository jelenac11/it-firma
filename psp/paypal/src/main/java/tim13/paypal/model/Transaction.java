package tim13.paypal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import tim13.paypal.enumeration.TransactionStatus;

@Entity
@Getter
@Setter
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String paymentId;

	@Column(nullable = false)
	private String merchantId;

	@Column(nullable = false)
	private String payerId;

	@Column(nullable = false)
	private String merchantOrderId;
	
	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false)
	private TransactionStatus status;

}
