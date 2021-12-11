package tim13.webshop.shop.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim13.webshop.shop.model.GeneralServiceShoppingCartItem;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralServiceShoppingCartItemDTO {

	private Long id;
	@NotBlank
	private String person;
	private ServiceDTO service;

	public GeneralServiceShoppingCartItemDTO(GeneralServiceShoppingCartItem i) {
		this.id = i.getId();
		this.person = i.getPerson();
		this.service = new ServiceDTO(i.getService());
	}

}
