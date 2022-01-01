package tim13.bank2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tim13.bank2.model.Account;
import tim13.bank2.model.CreditCard;
import tim13.bank2.repository.IAccountRepository;
import tim13.bank2.repository.ICreditCardRepository;

import java.time.LocalDate;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;

	@Autowired
	ICreditCardRepository creditCardRepo;

	@Autowired
	IAccountRepository accountRepo;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup)
			return;

		createCreditCardIfNotFound(1L, "0000001234567890", "488", "Pera Peric", LocalDate.parse("2022-01-01"));
		createCreditCardIfNotFound(2L, "0000001234567891", "481", "David Davidovic", LocalDate.parse("2022-02-01"));
		createCreditCardIfNotFound(3L, "0000001234567892", "482", "Miladin Milenkovic", LocalDate.parse("2022-03-01"));
		createCreditCardIfNotFound(4L, "0000001234567893", "483", "Jelena Nastasic", LocalDate.parse("2022-04-01"));
		createCreditCardIfNotFound(5L, "0000001234567894", "484", "Lenka Dundjerski", LocalDate.parse("2022-05-01"));
		createCreditCardIfNotFound(6L, "0000001234567895", "485", "Jasmina Brundic", LocalDate.parse("2022-06-01"));

		alreadySetup = true;
	}

	@Transactional
	public void createCreditCardIfNotFound(Long id, String pan, String securityCode, String cardHolderName,
			LocalDate expirationDate) {
		CreditCard card = creditCardRepo.findByAccountId(id);
		if (card == null) {
			card = new CreditCard();
			card.setCardHolderName(cardHolderName);
			card.setExpirationDate(expirationDate);
			card.setPan(pan);
			card.setSecurityCode(securityCode);
			Account account = accountRepo.getOne(id);
			card.setAccount(account);
			creditCardRepo.save(card);
		}
	}

}
