package tim13.webshop.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.dto.ChiefShoppingCartDTO;
import tim13.webshop.shop.dto.ChiefShoppingCartItemDTO;
import tim13.webshop.shop.exceptions.NotLoggedInException;
import tim13.webshop.shop.exceptions.RequestException;
import tim13.webshop.shop.model.ChiefShoppingCart;
import tim13.webshop.shop.model.ChiefShoppingCartItem;
import tim13.webshop.shop.model.Equipment;
import tim13.webshop.shop.model.User;
import tim13.webshop.shop.repositories.IChiefShoppingCartItemRepository;
import tim13.webshop.shop.repositories.IChiefShoppingCartRepository;
import tim13.webshop.shop.repositories.IEquipmentRepository;

@Service
public class ChiefShoppingCartService {

	@Autowired
	private IChiefShoppingCartRepository chiefShoppingCartRepository;

	@Autowired
	private IChiefShoppingCartItemRepository chiefShoppingCartItemRepository;

	@Autowired
	private IEquipmentRepository equipmentRepository;

	public ChiefShoppingCartDTO getMyCart() throws NotLoggedInException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		ChiefShoppingCart cart = chiefShoppingCartRepository.findByUserId(current.getId());

		return new ChiefShoppingCartDTO(cart);
	}

	public ResponseEntity<?> addItem(ChiefShoppingCartItemDTO dto) throws RequestException, NotLoggedInException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		ChiefShoppingCart cart = chiefShoppingCartRepository.findByUserId(current.getId());

		Equipment e = equipmentRepository.getOne(dto.getEquipment().getId());
		if (e == null)
			throw new RequestException("Equipment with ID " + dto.getEquipment().getId() + " doesn't exists.");

		boolean existing = false;
		for (ChiefShoppingCartItem itm : cart.getItems()) {
			if (itm.getEquipment().getId() == e.getId()) {
				existing = true;
				itm.setQuantity(itm.getQuantity() + dto.getQuantity());
				chiefShoppingCartItemRepository.save(itm);
				break;
			}
		}

		if (!existing) {
			ChiefShoppingCartItem item = new ChiefShoppingCartItem();
			item.setEquipment(e);
			item.setQuantity(dto.getQuantity());
			item.setCart(cart);
			chiefShoppingCartItemRepository.save(item);
		}

		return new ResponseEntity<>("Item " + dto.getEquipment().getName() + " added.", HttpStatus.OK);
	}

	public void removeItem(Long id) throws NotLoggedInException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		ChiefShoppingCart cart = chiefShoppingCartRepository.findByUserId(current.getId());

		for (ChiefShoppingCartItem item : cart.getItems()) {
			if (item.getId() == id) {
				chiefShoppingCartItemRepository.deleteById(id);
				break;
			}
		}
	}

}
