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
@DiscriminatorValue("SSC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceShoppingCart extends ShoppingCart {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private ServiceBuyer buyer;

	@OneToMany(mappedBy = "cart")
	private Set<ServiceShoppingCartItem> items;

}
