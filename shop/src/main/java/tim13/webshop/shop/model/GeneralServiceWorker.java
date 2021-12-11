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
@DiscriminatorValue("GS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralServiceWorker extends User {

	private static final long serialVersionUID = 1L;

	public GeneralServiceWorker(Long id) {
		super(id);
	}

	public GeneralServiceWorker(String email, String firstName, String lastName) {
		super(email, firstName, lastName);
	}

	public GeneralServiceWorker(String email, String password, String firstName, String lastName) {
		super(email, password, firstName, lastName);
	}

	public GeneralServiceWorker(Long id, String email, String password, String firstName, String lastName) {
		super(id, email, password, firstName, lastName);
	}

	@OneToOne(mappedBy = "worker", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private GeneralServiceShoppingCart shoppingCart;

}
