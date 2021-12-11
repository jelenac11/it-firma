package tim13.webshop.shop.dto;

import java.util.ArrayList;
import java.util.List;

import tim13.webshop.shop.model.ChiefShoppingCart;
import tim13.webshop.shop.model.ChiefShoppingCartItem;

public class ChiefShoppingCartDTO {

	public Long id;
	public List<ChiefShoppingCartItemDTO> items;

	public ChiefShoppingCartDTO(ChiefShoppingCart c) {
		this.id = c.getId();
		this.items = new ArrayList<ChiefShoppingCartItemDTO>();
		for (ChiefShoppingCartItem item : c.getItems()) {
			this.items.add(new ChiefShoppingCartItemDTO(item));
		}
	}

}
