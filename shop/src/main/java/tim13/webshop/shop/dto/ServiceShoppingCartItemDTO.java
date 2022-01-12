package tim13.webshop.shop.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim13.webshop.shop.model.ServiceShoppingCartItem;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceShoppingCartItemDTO {

	private Long id;
	@NotBlank
	private String person;
	private ServiceDTO service;
	private double additionalCosts;

	public ServiceShoppingCartItemDTO(ServiceShoppingCartItem i) {
		this.id = i.getId();
		this.person = i.getPerson();
		this.service = new ServiceDTO(i.getService());
		this.additionalCosts = i.getAdditionalCosts();
	}

}
