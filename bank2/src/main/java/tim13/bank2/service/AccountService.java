package tim13.bank2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.bank2.model.Account;
import tim13.bank2.model.CreditCard;
import tim13.bank2.repository.IAccountRepository;

@Service
public class AccountService {

	@Autowired
	private IAccountRepository accountRepo;

	public boolean hasEnoughMoney(double buyerAmount, CreditCard buyer) {
		Account account = accountRepo.findById(buyer.getId()).get();
		if (account != null && account.getBalance() >= buyerAmount) {
			account.setBalance(account.getBalance() - buyerAmount);
			accountRepo.save(account);
			return true;
		}
		return false;
	}

}
