package tim13.webshop.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.dto.CartDTO;
import tim13.webshop.shop.dto.EquipmentShoppingCartDTO;
import tim13.webshop.shop.dto.ServiceShoppingCartDTO;
import tim13.webshop.shop.enums.TransactionStatus;
import tim13.webshop.shop.model.EquipmentShoppingCart;
import tim13.webshop.shop.model.EquipmentShoppingCartItem;
import tim13.webshop.shop.model.Role;
import tim13.webshop.shop.model.ServiceShoppingCart;
import tim13.webshop.shop.model.ServiceShoppingCartItem;
import tim13.webshop.shop.model.Transaction;
import tim13.webshop.shop.model.User;
import tim13.webshop.shop.repositories.IEquipmentShoppingCartRepository;
import tim13.webshop.shop.repositories.IServiceShoppingCartRepository;
import tim13.webshop.shop.repositories.ITransactionRepository;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TransactionService {

	private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

	@Autowired
	private ITransactionRepository transactionRepository;

	@Autowired
	private IEquipmentShoppingCartRepository equipmentShoppingCartRepository;

	@Autowired
	private IServiceShoppingCartRepository serviceShoppingCartRepository;
	
	@Autowired
	private EmailService emailService;

	public Transaction save(Transaction transaction) {
		Transaction newTransaction = transactionRepository.save(transaction);

		logger.info("New transaction created");

		return newTransaction;
	}

	@Transactional
	public CartDTO update(Long id, int status) throws MailException, InterruptedException {
		Transaction transaction = transactionRepository.getOne(id);

		if (transaction == null) {
			return new CartDTO();
		}
		
		CartDTO cart = new CartDTO();

		switch (status) {
		case 1:
			transaction.setStatus(TransactionStatus.COMPLETED);
			User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			for (Role role : current.getRoles()) {
				if (role.getName().equals("ROLE_EQUIPMENT_BUYER")) {
					EquipmentShoppingCart equpmentCart = equipmentShoppingCartRepository.findByUserId(current.getId());
					cart.setEquipmentCart(new EquipmentShoppingCartDTO(equpmentCart));
					
					StringBuilder str = new StringBuilder();
		 
					str.append("You purchased:\n\n");
					for (EquipmentShoppingCartItem itm : equpmentCart.getItems()) {
						str.append(itm.getEquipment().getName() + "\nAmount: " + itm.getQuantity() + "\nPrice per item: " + itm.getEquipment().getPrice() + "\n\n");
					}
					
					emailService.sendEmail("jelenacupac99@gmail.com", "Purchase completed", str.toString());

					Set<EquipmentShoppingCartItem> forDelete = new HashSet<EquipmentShoppingCartItem>();
					equpmentCart.getItems().stream().forEach(forDelete::add);
					for (EquipmentShoppingCartItem item : forDelete) {
						equpmentCart.getItems().remove(item);
					}
					equipmentShoppingCartRepository.saveAndFlush(equpmentCart);

				} else if (role.getName().equals("ROLE_SERVICE_BUYER")) {
					ServiceShoppingCart serviceCart = serviceShoppingCartRepository.findByUserId(current.getId());
					cart.setServiceCart(new ServiceShoppingCartDTO(serviceCart));
					
					StringBuilder str = new StringBuilder();
					 
					str.append("You purchased:\n\n");
					for (ServiceShoppingCartItem itm : serviceCart.getItems()) {
						str.append(itm.getService().getName() + "\nFor: " + itm.getPerson() + "\nPrice: " + itm.getService().getPrice() + "\n\n");
					}
					emailService.sendEmail("jelenacupac99@gmail.com", "Purchase completed", str.toString());
					
					Set<ServiceShoppingCartItem> forDelete = new HashSet<ServiceShoppingCartItem>();
					serviceCart.getItems().stream().forEach(forDelete::add);
					for (ServiceShoppingCartItem item : forDelete) {
						serviceCart.getItems().remove(item);
					}
					serviceShoppingCartRepository.saveAndFlush(serviceCart);
				}
			}
			break;
		case 2:
			transaction.setStatus(TransactionStatus.FAILED);
			break;
		case 3:
			transaction.setStatus(TransactionStatus.CANCELLED);
			break;
		default:
			break;
		}

		transaction = transactionRepository.save(transaction);

		logger.info("Transaction with id " + transaction.getId() + " updated on status " + transaction.getStatus());

		return cart;
	}
}
