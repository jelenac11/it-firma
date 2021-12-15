package tim13.webshop.shop.model;

import java.util.Set;

import javax.persistence.Column;
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

	public Merchant(String email, String firstName, String lastName, String name) {
		super(email, firstName, lastName);
		this.name = name;
	}

	public Merchant(String email, String password, String firstName, String lastName, String name) {
		super(email, password, firstName, lastName);
		this.name = name;
	}

	public Merchant(Long id, String email, String password, String firstName, String lastName, String name) {
		super(id, email, password, firstName, lastName);
		this.name = name;
	}

	@Column
	private String name;

	@OneToMany(mappedBy = "merchant")
	private Set<Service> services;

	@OneToMany(mappedBy = "merchant")
	private Set<Equipment> equipments;

	@Column(name = "web_site_url")
	private String webSiteUrl;

	@Column(name = "success_url")
	private String successUrl;

	@Column(name = "fail_url")
	private String failUrl;

	@Column(name = "error_url")
	private String errorUrl;

}
