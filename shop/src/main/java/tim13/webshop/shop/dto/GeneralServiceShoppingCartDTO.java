package tim13.webshop.shop.dto;

import java.util.ArrayList;
import java.util.List;

import tim13.webshop.shop.model.GeneralServiceShoppingCart;
import tim13.webshop.shop.model.GeneralServiceShoppingCartItem;

public class GeneralServiceShoppingCartDTO {

	public Long id;
	public List<GeneralServiceShoppingCartItemDTO> items;

	public GeneralServiceShoppingCartDTO(GeneralServiceShoppingCart c) {
		this.id = c.getId();
		this.items = new ArrayList<GeneralServiceShoppingCartItemDTO>();
		for (GeneralServiceShoppingCartItem item : c.getItems()) {
			this.items.add(new GeneralServiceShoppingCartItemDTO(item));
		}
	}

}
