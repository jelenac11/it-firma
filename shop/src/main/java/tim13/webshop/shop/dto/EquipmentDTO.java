package tim13.webshop.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim13.webshop.shop.model.Equipment;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDTO {

	private Long id;
	private String name;
	private String description;
	private int price;
	private String equipmentType;

	public EquipmentDTO(Equipment e) {
		this.id = e.getId();
		this.name = e.getName();
		this.description = e.getDescription();
		this.price = e.getPrice();
		this.equipmentType = e.getEquipmentType().toString();
	}

}
