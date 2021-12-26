package tim13.bank.bank.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim13.bank.bank.converter.CryptoStringConverter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit_cards")
public class CreditCard {

	@Id
	@Column(name = "credit_card_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 16 digits
	// prvih sest cifara identifikuju banku
	@Column(name = "pan")
	@Convert(converter = CryptoStringConverter.class)
	private String pan;

	@Column(name = "security_code", nullable = false)
	@Convert(converter = CryptoStringConverter.class)
	private String securityCode;

	@Column(name = "card_holder_name", nullable = false)
	@Convert(converter = CryptoStringConverter.class)
	private String cardHolderName;

	@Column(nullable = false)
	private LocalDate expirationDate;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "account_id")
	private Account account;
}
