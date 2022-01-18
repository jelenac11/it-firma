package tim13.webshop.shop.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import tim13.webshop.shop.dto.OrderDTO;
import tim13.webshop.shop.dto.OrderDataDTO;
import tim13.webshop.shop.dto.PayWageDTO;
import tim13.webshop.shop.enums.TransactionStatus;
import tim13.webshop.shop.exceptions.NotLoggedInException;
import tim13.webshop.shop.exceptions.RequestException;
import tim13.webshop.shop.model.ItemType;
import tim13.webshop.shop.model.Merchant;
import tim13.webshop.shop.model.Order;
import tim13.webshop.shop.model.OrderItem;
import tim13.webshop.shop.model.Transaction;
import tim13.webshop.shop.model.User;
import tim13.webshop.shop.model.Wage;
import tim13.webshop.shop.repositories.IEquipmentShoppingCartItemRepository;
import tim13.webshop.shop.repositories.IServiceShoppingCartItemRepository;
import tim13.webshop.shop.repositories.IWageRepository;
import tim13.webshop.shop.repositories.IOrderRepository;

@Service
public class OrderService {

	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	private IServiceShoppingCartItemRepository generalServiceShoppingCartItemRepository;

	@Autowired
	private IEquipmentShoppingCartItemRepository chiefShoppingCartItemRepository;

	@Autowired
	private IWageRepository wageRepository;

	@Autowired
	private TransactionService transactionService;

	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	public Order getOrderByTransaction(Long transactionId) {
		logger.trace("Read order from DB by transaction id.");
		return orderRepository.getByTransactionId(transactionId);
	}

	public RedirectView addOrder(OrderDTO dto) throws RequestException, NotLoggedInException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		Order o = new Order();
		o.setTotalPrice(dto.getTotalPrice());

		List<OrderItem> items = dto.getItems().stream()
				.map(c -> new OrderItem(null, c.getItemId(), c.getProductId(), ItemType.valueOf(c.getItemType()), o))
				.collect(Collectors.toList());

		o.setItems(new HashSet<>(items));

		orderRepository.save(o);
		logger.info(String.format("New order for user with ID: %s created.", current.getId()));

		Merchant merchant = null;

		if (dto.getItems().get(0).getItemType().equals("EQUIPMENT")) {
			merchant = chiefShoppingCartItemRepository.findById(dto.getItems().get(0).getItemId()).get().getEquipment()
					.getMerchant();
		} else if (dto.getItems().get(0).getItemType().equals("SERVICE")) {
			merchant = generalServiceShoppingCartItemRepository.findById(dto.getItems().get(0).getItemId()).get()
					.getService().getMerchant();
		}

		Transaction transaction = createAndSaveTransaction(merchant, current, o);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		OrderDataDTO orderDataDTO = new OrderDataDTO();
		orderDataDTO.setTransactionId(transaction.getId());
		orderDataDTO.setMerchantEmail(merchant.getEmail());
		orderDataDTO.setTimestamp(transaction.getTimeStamp());
		orderDataDTO.setTotalPrice(o.getTotalPrice());
		orderDataDTO.setSuccessUrl(merchant.getSuccessUrl());
		orderDataDTO.setFailUrl(merchant.getFailUrl());
		orderDataDTO.setErrorUrl(merchant.getErrorUrl());

		HttpEntity<OrderDataDTO> entity = new HttpEntity<>(orderDataDTO, headers);

		RestTemplate restTemplate = new RestTemplate();

		Long orderDataId = restTemplate
				.postForEntity("https://localhost:8095/api/order-data", entity, Long.class).getBody();

		return new RedirectView("https://localhost:8096/#/checkout/" + orderDataId);
	}

	public RedirectView addWage(PayWageDTO dto) throws RequestException, NotLoggedInException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		Wage wage = wageRepository.findById(dto.getWageId()).orElseGet(null);

		Order o = new Order();
		o.setTotalPrice(dto.getPrice());

		List<OrderItem> items = new ArrayList<>();

		items.add(new OrderItem(null, null, wage.getId(), ItemType.SERVICE, o));

		o.setItems(new HashSet<>(items));

		orderRepository.save(o);

		logger.info(String.format("New order wage for user with ID: %s created.", current.getId()));

		Merchant merchant = null;

		Transaction transaction = createAndSaveTransaction(merchant, current, o);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		OrderDataDTO orderDataDTO = new OrderDataDTO();
		orderDataDTO.setTransactionId(transaction.getId());
		orderDataDTO.setMerchantEmail("someemail@gmail.com");
		orderDataDTO.setTimestamp(transaction.getTimeStamp());
		orderDataDTO.setTotalPrice(o.getTotalPrice());
		orderDataDTO.setSuccessUrl("https://f333-149-28-36-86.ngrok.io/success");
		orderDataDTO.setFailUrl("https://f333-149-28-36-86.ngrok.io/fail");
		orderDataDTO.setErrorUrl("https://f333-149-28-36-86.ngrok.io/error");
		orderDataDTO.setReceiver(dto.getReceiver());

		HttpEntity<OrderDataDTO> entity = new HttpEntity<>(orderDataDTO, headers);

		RestTemplate restTemplate = new RestTemplate();

		Long orderDataId = restTemplate
				.postForEntity("https://localhost:8095/api/order-data", entity, Long.class).getBody();

		return new RedirectView("https://localhost:8096/#/checkout/" + orderDataId + "?wage=yes");
	}

	private Transaction createAndSaveTransaction(Merchant to, User user, Order order) {
		Transaction transaction = createTransaction(to, user, order);

		transaction = transactionService.save(transaction);
		logger.info("New transaction created.");

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
