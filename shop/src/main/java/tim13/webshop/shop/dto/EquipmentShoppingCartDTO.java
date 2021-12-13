package tim13.webshop.shop.dto;

import java.util.ArrayList;
import java.util.List;

import tim13.webshop.shop.model.EquipmentShoppingCart;
import tim13.webshop.shop.model.EquipmentShoppingCartItem;

public class EquipmentShoppingCartDTO {

	public Long id;
	public List<EquipmentShoppingCartItemDTO> items;

	public EquipmentShoppingCartDTO(EquipmentShoppingCart c) {
		this.id = c.getId();
		this.items = new ArrayList<EquipmentShoppingCartItemDTO>();
		for (EquipmentShoppingCartItem item : c.getItems()) {
			this.items.add(new EquipmentShoppingCartItemDTO(item));
		}
	}

}
