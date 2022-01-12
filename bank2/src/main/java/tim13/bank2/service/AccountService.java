package tim13.bank2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.bank2.model.Account;
import tim13.bank2.model.CreditCard;
import tim13.bank2.repository.IAccountRepository;

@Service
public class AccountService {

	@Autowired
	private IAccountRepository accountRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	public boolean hasEnoughMoney(double buyerAmount, CreditCard buyer) {
		logger.trace("Checking if there is enough money on bank account **** **** **** " + buyer.getPan().substring(12));
		Account account = accountRepo.findById(buyer.getId()).get();
		if (account != null && account.getBalance() >= buyerAmount) {
			logger.info("Enough money on bank account **** **** **** " + buyer.getPan().substring(12));
			account.setBalance(account.getBalance() - buyerAmount);
			accountRepo.save(account);
			return true;
		}
		logger.trace("Not enough money on bank account **** **** **** " + buyer.getPan().substring(12));
		return false;
	}

}
