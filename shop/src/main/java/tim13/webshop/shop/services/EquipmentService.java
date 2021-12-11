package tim13.webshop.shop.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.dto.EquipmentDTO;
import tim13.webshop.shop.model.Equipment;
import tim13.webshop.shop.repositories.IEquipmentRepository;

@Service
public class EquipmentService {

	@Autowired
	private IEquipmentRepository equipmentRepository;

	public List<EquipmentDTO> findAll() {
		List<EquipmentDTO> forReturn = new ArrayList<EquipmentDTO>();
		List<Equipment> equipment = equipmentRepository.findAll();
		for (Equipment eq : equipment) {
			forReturn.add(new EquipmentDTO(eq));
		}
		return forReturn;
	}

}
