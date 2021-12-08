package tim13.webshop.shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "general_service_shopping_cart_items")
public class GeneralServiceShoppingCartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "general_service_shopping_cart_item_id")
	private Long id;

	@Column
	private String person;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "shopping_cart_id")
	private GeneralServiceShoppingCart cart;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_id")
	private Service service;

}
