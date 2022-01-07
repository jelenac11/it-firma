package tim13.bank.bank.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.bank.bank.model.Account;
import tim13.bank.bank.model.CreditCard;
import tim13.bank.bank.repository.IAccountRepository;

@Service
public class AccountService {

	@Autowired
	private IAccountRepository accountRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	public boolean hasEnoughMoney(double buyerAmount, CreditCard buyer) {
		Account account = accountRepo.findById(buyer.getId()).get();
		logger.trace("Checking if there is enough money on bank account");
        if(account != null && account.getBalance() >= buyerAmount){
        	logger.info("Enough money on bank account");
            account.setBalance(account.getBalance() - buyerAmount);
            accountRepo.save(account);
            return true;
        }
        logger.info("Not enough money on bank account");
        return false;
	}
	
	
}
