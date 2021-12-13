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
@Entity(name = "equipment_shopping_cart_items")
public class EquipmentShoppingCartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "equipment_shopping_cart_item_id")
	private Long id;

	@Column
	private int quantity;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "shopping_cart_id")
	private EquipmentShoppingCart cart;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipment_id")
	private Equipment equipment;

}
