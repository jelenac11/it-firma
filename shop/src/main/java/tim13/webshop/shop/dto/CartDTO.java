package tim13.webshop.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {

	public EquipmentShoppingCartDTO equipmentCart;
	public ServiceShoppingCartDTO serviceCart;

}
