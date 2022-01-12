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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "order_items")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id")
	private Long id;

	@Column(name = "item_id")
	private Long itemId;
	
	@Column(name = "product_id")
	private Long productId;

	@Column(name = "item_type")
	@Enumerated(EnumType.STRING)
	private ItemType itemType;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "order_id")
	private Order order;

}
