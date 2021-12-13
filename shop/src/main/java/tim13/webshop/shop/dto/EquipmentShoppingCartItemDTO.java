package tim13.webshop.shop.dto;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim13.webshop.shop.model.EquipmentShoppingCartItem;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentShoppingCartItemDTO {

	private Long id;
	@Min(1)
	private int quantity;
	private EquipmentDTO equipment;

	public EquipmentShoppingCartItemDTO(EquipmentShoppingCartItem i) {
		this.id = i.getId();
		this.quantity = i.getQuantity();
		this.equipment = new EquipmentDTO(i.getEquipment());
	}

}
