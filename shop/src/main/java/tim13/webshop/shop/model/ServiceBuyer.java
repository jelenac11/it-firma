package tim13.webshop.shop.model;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("SB")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceBuyer extends User {

	private static final long serialVersionUID = 1L;

	public ServiceBuyer(Long id) {
		super(id);
	}

	public ServiceBuyer(String email, String firstName, String lastName) {
		super(email, firstName, lastName);
	}

	public ServiceBuyer(String email, String password, String firstName, String lastName) {
		super(email, password, firstName, lastName);
	}

	public ServiceBuyer(Long id, String email, String password, String firstName, String lastName) {
		super(id, email, password, firstName, lastName);
	}

	@OneToOne(mappedBy = "buyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ServiceShoppingCart shoppingCart;

}
