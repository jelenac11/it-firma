package tim13.webshop.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.dto.GeneralServiceShoppingCartDTO;
import tim13.webshop.shop.dto.GeneralServiceShoppingCartItemDTO;
import tim13.webshop.shop.exceptions.NotLoggedInException;
import tim13.webshop.shop.exceptions.RequestException;
import tim13.webshop.shop.model.GeneralServiceShoppingCart;
import tim13.webshop.shop.model.GeneralServiceShoppingCartItem;
import tim13.webshop.shop.model.User;
import tim13.webshop.shop.repositories.IGeneralServiceShoppingCartItemRepository;
import tim13.webshop.shop.repositories.IGeneralServiceShoppingCartRepository;
import tim13.webshop.shop.repositories.IServiceRepository;

@Service
public class GeneralServiceShoppingCartService {

	@Autowired
	private IGeneralServiceShoppingCartRepository generalServiceShoppingCartRepository;

	@Autowired
	private IGeneralServiceShoppingCartItemRepository generalServiceShoppingCartItemRepository;

	@Autowired
	private IServiceRepository serviceRepository;

	public GeneralServiceShoppingCartDTO getMyCart() throws NotLoggedInException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		GeneralServiceShoppingCart cart = generalServiceShoppingCartRepository.findByUserId(current.getId());

		return new GeneralServiceShoppingCartDTO(cart);
	}

	public ResponseEntity<?> addItem(GeneralServiceShoppingCartItemDTO dto)
			throws RequestException, NotLoggedInException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		GeneralServiceShoppingCart cart = generalServiceShoppingCartRepository.findByUserId(current.getId());

		tim13.webshop.shop.model.Service s = serviceRepository.getOne(dto.getService().getId());
		if (s == null)
			throw new RequestException("Service with ID " + dto.getService().getId() + " doesn't exists.");

		GeneralServiceShoppingCartItem item = new GeneralServiceShoppingCartItem();
		item.setService(s);
		item.setPerson(dto.getPerson());
		item.setCart(cart);

		generalServiceShoppingCartItemRepository.save(item);
		return new ResponseEntity<>("Item " + dto.getService().getName() + " added.", HttpStatus.OK);
	}

	public void removeItem(Long id) throws NotLoggedInException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		GeneralServiceShoppingCart cart = generalServiceShoppingCartRepository.findByUserId(current.getId());

		for (GeneralServiceShoppingCartItem item : cart.getItems()) {
			if (item.getId() == id) {
				generalServiceShoppingCartItemRepository.deleteById(id);
				break;
			}
		}
	}

}
