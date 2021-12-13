package tim13.webshop.shop.dto;

import java.util.ArrayList;
import java.util.List;

import tim13.webshop.shop.model.ServiceShoppingCart;
import tim13.webshop.shop.model.ServiceShoppingCartItem;

public class ServiceShoppingCartDTO {

	public Long id;
	public List<ServiceShoppingCartItemDTO> items;

	public ServiceShoppingCartDTO(ServiceShoppingCart c) {
		this.id = c.getId();
		this.items = new ArrayList<ServiceShoppingCartItemDTO>();
		for (ServiceShoppingCartItem item : c.getItems()) {
			this.items.add(new ServiceShoppingCartItemDTO(item));
		}
	}

}
