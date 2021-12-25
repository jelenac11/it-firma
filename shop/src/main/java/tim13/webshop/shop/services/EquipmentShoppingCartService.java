package tim13.webshop.shop.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.dto.EquipmentShoppingCartDTO;
import tim13.webshop.shop.dto.EquipmentShoppingCartItemDTO;
import tim13.webshop.shop.exceptions.NotLoggedInException;
import tim13.webshop.shop.exceptions.RequestException;
import tim13.webshop.shop.model.EquipmentShoppingCart;
import tim13.webshop.shop.model.EquipmentShoppingCartItem;
import tim13.webshop.shop.model.Equipment;
import tim13.webshop.shop.model.User;
import tim13.webshop.shop.repositories.IEquipmentShoppingCartItemRepository;
import tim13.webshop.shop.repositories.IEquipmentShoppingCartRepository;
import tim13.webshop.shop.repositories.IEquipmentRepository;

@Service
public class EquipmentShoppingCartService {

	@Autowired
	private IEquipmentShoppingCartRepository equipmentShoppingCartRepository;

	@Autowired
	private IEquipmentShoppingCartItemRepository equipmentShoppingCartItemRepository;

	@Autowired
	private IEquipmentRepository equipmentRepository;

	private static final Logger logger = LoggerFactory.getLogger(EquipmentShoppingCartService.class);

	public EquipmentShoppingCartDTO getMyCart() throws NotLoggedInException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		EquipmentShoppingCart cart = equipmentShoppingCartRepository.findByUserId(current.getId());

		logger.info("Reading equipment shopping cart from database.");
		return new EquipmentShoppingCartDTO(cart);
	}

	public ResponseEntity<?> addItem(EquipmentShoppingCartItemDTO dto) throws RequestException, NotLoggedInException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		EquipmentShoppingCart cart = equipmentShoppingCartRepository.findByUserId(current.getId());

		Equipment e = equipmentRepository.getOne(dto.getEquipment().getId());
		if (e == null)
			throw new RequestException("Equipment with ID " + dto.getEquipment().getId() + " doesn't exists.");

		for (EquipmentShoppingCartItem itm : cart.getItems()) {
			if (itm.getEquipment().getMerchant().getId() != e.getMerchant().getId()) {
				throw new RequestException("You can't add to cart items from multiple merchants.");
			}
		}

		boolean existing = false;
		for (EquipmentShoppingCartItem itm : cart.getItems()) {
			if (itm.getEquipment().getId() == e.getId()) {
				existing = true;
				itm.setQuantity(itm.getQuantity() + dto.getQuantity());
				equipmentShoppingCartItemRepository.save(itm);
				break;
			}
		}

		if (!existing) {
			EquipmentShoppingCartItem item = new EquipmentShoppingCartItem();
			item.setEquipment(e);
			item.setQuantity(dto.getQuantity());
			item.setCart(cart);
			equipmentShoppingCartItemRepository.save(item);
		}

		logger.info("Adding new item to equipment shopping cart.");
		return new ResponseEntity<>("Item " + dto.getEquipment().getName() + " added.", HttpStatus.OK);
	}

	public void removeItem(Long id) throws NotLoggedInException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		EquipmentShoppingCart cart = equipmentShoppingCartRepository.findByUserId(current.getId());

		for (EquipmentShoppingCartItem item : cart.getItems()) {
			if (item.getId() == id) {
				equipmentShoppingCartItemRepository.deleteById(id);
				logger.info("Item removed from equipment shopping cart.");
				break;
			}
		}
	}

}
