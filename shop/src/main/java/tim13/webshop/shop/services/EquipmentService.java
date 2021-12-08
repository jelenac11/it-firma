package tim13.webshop.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.repositories.IEquipmentRepository;

@Service
public class EquipmentService {

	@Autowired
	private IEquipmentRepository equipmentRepository;

}
