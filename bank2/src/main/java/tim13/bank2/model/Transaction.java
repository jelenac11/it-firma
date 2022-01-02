package tim13.bank2.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim13.bank2.converter.CryptoStringConverter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false)
	private Long acquirerOrderId;

	@Column(nullable = false)
	private Timestamp issuerTimestamp;

	@Column
	@Convert(converter = CryptoStringConverter.class)
	private String pan;

	@Enumerated(EnumType.STRING)
	private TransactionStatus status;

	public Transaction(Double amount, Long acquirerOrderId, Timestamp issuerTimestamp, String pan) {
		this.amount = amount;
		this.acquirerOrderId = acquirerOrderId;
		this.issuerTimestamp = issuerTimestamp;
		this.pan = pan;
	}

	public Transaction(Double amount, Long acquirerOrderId, Timestamp issuerTimestamp, String pan,
			TransactionStatus status) {
		this.amount = amount;
		this.acquirerOrderId = acquirerOrderId;
		this.issuerTimestamp = issuerTimestamp;
		this.pan = pan;
		this.status = status;
	}

}
