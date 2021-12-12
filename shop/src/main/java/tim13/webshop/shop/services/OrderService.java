package tim13.webshop.shop.services;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import tim13.webshop.shop.dto.OrderDTO;
import tim13.webshop.shop.exceptions.NotLoggedInException;
import tim13.webshop.shop.exceptions.RequestException;
import tim13.webshop.shop.model.ItemType;
import tim13.webshop.shop.model.Order;
import tim13.webshop.shop.model.OrderItem;
import tim13.webshop.shop.model.User;
import tim13.webshop.shop.repositories.IChiefShoppingCartItemRepository;
import tim13.webshop.shop.repositories.IGeneralServiceShoppingCartItemRepository;
import tim13.webshop.shop.repositories.IOrderRepository;

@Service
public class OrderService {

	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	private IGeneralServiceShoppingCartItemRepository generalServiceShoppingCartItemRepository;

	@Autowired
	private IChiefShoppingCartItemRepository chiefShoppingCartItemRepository;

	public RedirectView addOrder(OrderDTO dto) throws RequestException, NotLoggedInException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		Order o = new Order();
		o.setTotalPrice(dto.getTotalPrice());

		List<OrderItem> items = dto.getItems().stream()
				.map(c -> new OrderItem(null, c.getItemId(), ItemType.valueOf(c.getItemType()), o))
				.collect(Collectors.toList());

		o.setItems(new HashSet<>(items));

		orderRepository.save(o);

		String merchant = "";

		if (dto.getItems().get(0).getItemType().equals("EQUIPMENT")) {
			merchant = chiefShoppingCartItemRepository.findById(dto.getItems().get(0).getItemId()).get().getEquipment()
					.getMerchant().getEmail();
		} else if (dto.getItems().get(0).getItemType().equals("SERVICE")) {
			merchant = generalServiceShoppingCartItemRepository.findById(dto.getItems().get(0).getItemId()).get()
					.getService().getMerchant().getEmail();
		}

		return new RedirectView(
				"http://localhost:8096/#/checkout/" + 1 + "/" + merchant + "/" + o.getTotalPrice());
	}

}
