package tim13.webshop.shop.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.dto.EquipmentDTO;
import tim13.webshop.shop.model.Equipment;
import tim13.webshop.shop.repositories.IEquipmentRepository;

@Service
public class EquipmentService {

	@Autowired
	private IEquipmentRepository equipmentRepository;

	private static final Logger logger = LoggerFactory.getLogger(EquipmentService.class);

	public List<EquipmentDTO> findAll() {
		List<EquipmentDTO> forReturn = new ArrayList<EquipmentDTO>();
		List<Equipment> equipment = equipmentRepository.findAll();
		for (Equipment eq : equipment) {
			forReturn.add(new EquipmentDTO(eq));
		}
		logger.info("Reading equipment from database");
		return forReturn;
	}

}
