package tim13.webshop.shop.model;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("ESC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentShoppingCart extends ShoppingCart {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private EquipmentBuyer buyer;

	@OneToMany(mappedBy = "cart")
	private Set<EquipmentShoppingCartItem> items;

}
