package tim13.webshop.shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import tim13.webshop.shop.enums.TransactionStatus;

@Entity
@Getter
@Setter
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer", referencedColumnName = "user_id")
	private User from;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "merchant", referencedColumnName = "user_id")
	private Merchant to;

	@Column
	private Long timeStamp;

	@OneToOne
	@JoinColumn(name = "order_id", referencedColumnName = "order_id")
	private Order order;

	@Column
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;
}
