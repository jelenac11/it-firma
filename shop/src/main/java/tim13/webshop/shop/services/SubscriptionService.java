package tim13.webshop.shop.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import tim13.webshop.shop.dto.SubscriptionDTO;
import tim13.webshop.shop.dto.SubscriptionForPSPDTO;
import tim13.webshop.shop.dto.UnsubscribeDTO;
import tim13.webshop.shop.enums.OrderType;
import tim13.webshop.shop.enums.TransactionStatus;
import tim13.webshop.shop.exceptions.BaseException;
import tim13.webshop.shop.mapper.SubscriptionMapper;
import tim13.webshop.shop.model.ItemType;
import tim13.webshop.shop.model.Merchant;
import tim13.webshop.shop.model.Order;
import tim13.webshop.shop.model.OrderItem;
import tim13.webshop.shop.model.Plan;
import tim13.webshop.shop.model.Subscription;
import tim13.webshop.shop.model.Transaction;
import tim13.webshop.shop.model.User;
import tim13.webshop.shop.repositories.IOrderRepository;
import tim13.webshop.shop.repositories.IPlanRepository;
import tim13.webshop.shop.repositories.ISubscriptionRepository;
import tim13.webshop.shop.repositories.ITransactionRepository;

@Service
public class SubscriptionService {

	@Autowired
	private ISubscriptionRepository subscriptionRepository;

	@Autowired
	private IPlanRepository planRepository;

	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	private ITransactionRepository transactionRepository;

	@Autowired
	private SubscriptionMapper mapper;

	private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

	public List<SubscriptionDTO> getAllByBuyer() throws BaseException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (current == null) {
			logger.debug("You need to be logged in.");

			throw new BaseException(HttpStatus.UNAUTHORIZED, "You need to be logged in.");
		}

		return subscriptionRepository.findByBuyer(current).stream().map(subs -> mapper.toDTO(subs))
				.collect(Collectors.toList());
	}

	public String subscribe(Long planId) throws BaseException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (current == null) {
			logger.debug("You need to be logged in.");

			throw new BaseException(HttpStatus.UNAUTHORIZED, "You need to be logged in.");
		}

		Plan plan = planRepository.findById(planId).orElseGet(null);

		if (plan == null) {
			logger.debug(String.format("Plan with id %s doesn't exist.", planId));

			throw new BaseException(HttpStatus.NOT_FOUND, String.format("Plan with id %s doesn't exist.", planId));
		}

		Order order = createOrder(plan);

		OrderItem orderItem = createOrderItem(plan, order);

		Set<OrderItem> orderItems = new HashSet<OrderItem>();
		orderItems.add(orderItem);

		order.setItems(orderItems);

		orderRepository.save(order);

		logger.info(String.format("New order for user with ID: %s created.", current.getId()));

		Transaction transaction = createAndSaveTransaction(plan.getCourse().getMerchant(), current, order);

		SubscriptionForPSPDTO pspdto = createSubscriptionForPSPDTO(plan, transaction.getId());

		String url = sendRequestToSubscribeUserOnPlan(pspdto);

		if (url == null) {
			return expandUrlWithId(plan.getCourse().getMerchant().getErrorUrl(), transaction.getId());
		}

		return url;
	}

	public void unsubscribe(Long subscriptionId, UnsubscribeDTO dto) throws BaseException {
		Optional<Subscription> subscription = subscriptionRepository.findById(subscriptionId);

		if (subscription.isEmpty()) {
			logger.debug(String.format("Subscription with id %s doesn't exist.", subscriptionId));

			throw new BaseException(HttpStatus.NOT_FOUND,
					String.format("Subscription with id %s doesn't exist.", subscriptionId));
		}

		String paypalSubscriptionId = subscription.get().getSubscriptionPaypalId();

		logger.info("Sending request to unsubscribe user from subscription.");

		sendRequestToUnsubscribe(paypalSubscriptionId, dto);

		logger.info("Removing subscription from database.");

		subscriptionRepository.delete(subscription.get());

		logger.info("User successfully unsubscribed.");
	}

	public void createSubscription(Long transactionId, String subscriptionId) throws BaseException {
		Transaction transaction = transactionRepository.getOne(transactionId);

		if (transaction == null) {
			logger.debug("Transaction doesn't exist.");

			throw new BaseException(HttpStatus.NOT_FOUND, "Transaction doesn't exist.");
		}

		Plan plan = planRepository.getOne(transaction.getOrder().getItems().stream().findFirst().get().getItemId());

		if (plan == null) {
			logger.debug("Plan doesn't exist.");

			throw new BaseException(HttpStatus.NOT_FOUND, "Plan doesn't exist.");
		}

		Subscription subscription = new Subscription();

		subscription.setStartDate(new Date());
		subscription.setSubscriptionPaypalId(subscriptionId);
		subscription.setPlan(plan);
		subscription.setBuyer(transaction.getFrom());

		logger.info("Saving new subscription in database.");

		subscription = subscriptionRepository.save(subscription);
	}

	private Order createOrder(Plan plan) {
		Order order = new Order();

		order.setOrderType(OrderType.SUBSCRIPTION);
		order.setTotalPrice(plan.getPrice());

		return order;
	}

	private OrderItem createOrderItem(Plan plan, Order order) {
		OrderItem orderItem = new OrderItem();

		orderItem.setItemId(plan.getId());
		orderItem.setItemType(ItemType.SERVICE);
		orderItem.setOrder(order);
		return orderItem;
	}

	private Transaction createAndSaveTransaction(Merchant to, User user, Order order) {
		Transaction transaction = createTransaction(to, user, order);

		transaction = transactionRepository.save(transaction);

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

	private SubscriptionForPSPDTO createSubscriptionForPSPDTO(Plan plan, Long transactionId) {
		Merchant merchant = plan.getCourse().getMerchant();

		SubscriptionForPSPDTO pspdto = new SubscriptionForPSPDTO();

		pspdto.setPlanId(plan.getPaypalPlanId());
		pspdto.setSuccessUrl(merchant.getSuccessUrl());
		pspdto.setCancelUrl(merchant.getFailUrl());
		pspdto.setErrorUrl(merchant.getErrorUrl());
		pspdto.setTransactionId(transactionId);

		return pspdto;
	}

	private String sendRequestToSubscribeUserOnPlan(SubscriptionForPSPDTO data) throws BaseException {
		RestTemplate rs = new RestTemplate();

		try {
			return rs.postForEntity("http://localhost:8095/api/subscription/subscribe", data, String.class).getBody();
		} catch (HttpStatusCodeException e) {
			logger.debug(e.getResponseBodyAsString());

			return null;
		}
	}

	private void sendRequestToUnsubscribe(String subscriptionId, UnsubscribeDTO dto) throws BaseException {
		RestTemplate rs = new RestTemplate();

		try {
			rs.postForEntity("http://localhost:8095/api/subscription/unsubscribe/" + subscriptionId, dto, Void.class);
		} catch (HttpStatusCodeException e) {
			logger.debug(e.getResponseBodyAsString());

			throw new BaseException(HttpStatus.resolve(e.getRawStatusCode()), e.getResponseBodyAsString());
		}
	}

	private String expandUrlWithId(String url, Long id) {
		return url + "/" + id;
	}
}
