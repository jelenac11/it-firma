package tim13.webshop.shop.services;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import tim13.webshop.shop.dto.OrderDTO;
import tim13.webshop.shop.enums.TransactionStatus;
import tim13.webshop.shop.exceptions.NotLoggedInException;
import tim13.webshop.shop.exceptions.RequestException;
import tim13.webshop.shop.model.ItemType;
import tim13.webshop.shop.model.Merchant;
import tim13.webshop.shop.model.Order;
import tim13.webshop.shop.model.OrderItem;
import tim13.webshop.shop.model.Transaction;
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

	@Autowired
	private TransactionService transactionService;

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

		Merchant merchant = null;

		if (dto.getItems().get(0).getItemType().equals("EQUIPMENT")) {
			merchant = chiefShoppingCartItemRepository.findById(dto.getItems().get(0).getItemId()).get().getEquipment()
					.getMerchant();
		} else if (dto.getItems().get(0).getItemType().equals("SERVICE")) {
			merchant = generalServiceShoppingCartItemRepository.findById(dto.getItems().get(0).getItemId()).get()
					.getService().getMerchant();
		}

		Transaction transaction = createAndSaveTransaction(merchant, current, o);

		return new RedirectView("http://localhost:8096/#/checkout/" + transaction.getId() + "/" + merchant.getEmail()
				+ "/" + o.getTotalPrice());
	}

	private Transaction createAndSaveTransaction(Merchant to, User user, Order order) {
		Transaction transaction = createTransaction(to, user, order);

		transaction = transactionService.save(transaction);

		return transaction;
	}

	private Transaction createTransaction(Merchant to, User user, Order order) {
		Transaction transaction = new Transaction();

		transaction.setFrom(user);
		transaction.setTo(to);
		transaction.setOrder(order);
		transaction.setStatus(TransactionStatus.CREATED);
		transaction.setTimeStamp(System.currentTimeMillis());

		return transaction;
	}

}
