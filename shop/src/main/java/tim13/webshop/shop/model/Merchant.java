package tim13.webshop.shop.model;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("ME")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Merchant extends User {

	private static final long serialVersionUID = 1L;

	public Merchant(Long id) {
		super(id);
	}

	public Merchant(String email, String firstName, String lastName) {
		super(email, firstName, lastName);
	}

	public Merchant(String email, String password, String firstName, String lastName) {
		super(email, password, firstName, lastName);
	}

	public Merchant(Long id, String email, String password, String firstName, String lastName) {
		super(id, email, password, firstName, lastName);
	}

	@OneToMany(mappedBy = "merchant")
	private Set<Service> services;

	@OneToMany(mappedBy = "merchant")
	private Set<Equipment> equipments;

}
