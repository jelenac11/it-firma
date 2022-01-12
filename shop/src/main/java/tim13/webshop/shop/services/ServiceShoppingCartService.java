package tim13.webshop.shop.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.dto.ServiceShoppingCartDTO;
import tim13.webshop.shop.dto.ServiceShoppingCartItemDTO;
import tim13.webshop.shop.exceptions.NotLoggedInException;
import tim13.webshop.shop.exceptions.RequestException;
import tim13.webshop.shop.model.ServiceShoppingCart;
import tim13.webshop.shop.model.ServiceShoppingCartItem;
import tim13.webshop.shop.model.User;
import tim13.webshop.shop.repositories.IServiceShoppingCartItemRepository;
import tim13.webshop.shop.repositories.IServiceShoppingCartRepository;
import tim13.webshop.shop.repositories.IServiceRepository;

@Service
public class ServiceShoppingCartService {

	@Autowired
	private IServiceShoppingCartRepository serviceShoppingCartRepository;

	@Autowired
	private IServiceShoppingCartItemRepository serviceShoppingCartItemRepository;

	@Autowired
	private IServiceRepository serviceRepository;

	private static final Logger logger = LoggerFactory.getLogger(ServiceShoppingCartService.class);

	public ServiceShoppingCartDTO getMyCart() throws NotLoggedInException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		ServiceShoppingCart cart = serviceShoppingCartRepository.findByUserId(current.getId());

		logger.info("Reading service shopping cart from database.");
		return new ServiceShoppingCartDTO(cart);
	}

	public ResponseEntity<?> addItem(ServiceShoppingCartItemDTO dto) throws RequestException, NotLoggedInException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		ServiceShoppingCart cart = serviceShoppingCartRepository.findByUserId(current.getId());

		tim13.webshop.shop.model.Service s = serviceRepository.getOne(dto.getService().getId());
		if (s == null)
			throw new RequestException("Service with ID " + dto.getService().getId() + " doesn't exists.");

		for (ServiceShoppingCartItem itm : cart.getItems()) {
			if (itm.getService().getMerchant().getId() != s.getMerchant().getId()) {
				throw new RequestException("You can't add to cart items from multiple merchants.");
			}
		}

		ServiceShoppingCartItem item = new ServiceShoppingCartItem();
		item.setService(s);
		item.setPerson(dto.getPerson());
		item.setAdditionalCosts(dto.getAdditionalCosts());
		item.setCart(cart);

		item = serviceShoppingCartItemRepository.save(item);
		logger.info("Adding new item with id " + item.getId() + " to service shopping cart.");

		return new ResponseEntity<>("Item " + dto.getService().getName() + " added.", HttpStatus.OK);
	}

	public void removeItem(Long id) throws NotLoggedInException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		ServiceShoppingCart cart = serviceShoppingCartRepository.findByUserId(current.getId());

		for (ServiceShoppingCartItem item : cart.getItems()) {
			if (item.getId() == id) {
				cart.getItems().remove(item);
				serviceShoppingCartRepository.saveAndFlush(cart);
				logger.info("Item with id " + item.getId() + " removed from service shopping cart.");
				break;
			}
		}
	}

}
