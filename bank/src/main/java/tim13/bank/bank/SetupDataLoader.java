package tim13.bank.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tim13.bank.bank.model.Account;
import tim13.bank.bank.model.CreditCard;
import tim13.bank.bank.model.Merchant;
import tim13.bank.bank.repository.IAccountRepository;
import tim13.bank.bank.repository.ICreditCardRepository;
import tim13.bank.bank.repository.IMerchantRepository;

import java.time.LocalDate;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;
	
	@Autowired
	ICreditCardRepository creditCardRepo;
	
	@Autowired
	IAccountRepository accountRepo;
	
	@Autowired
	IMerchantRepository merchantRepo;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup)
			return;

		createCreditCardIfNotFound(1L, "1234561234567890", "488", "Chief Chiefovic", LocalDate.parse("2022-01-01"));
		createCreditCardIfNotFound(2L, "1234561234567891", "489", "Laza Lazic", LocalDate.parse("2022-02-01"));
		createCreditCardIfNotFound(3L, "1234561234567892", "481", "Milica Jovanovic", LocalDate.parse("2022-03-01"));
		createCreditCardIfNotFound(4L, "1234561234567893", "482", "Milan Milinkovic", LocalDate.parse("2022-04-01"));
		createCreditCardIfNotFound(5L, "1234561234567894", "483", "Jovan Memedovicc", LocalDate.parse("2022-05-01"));
		createCreditCardIfNotFound(6L, "1234561234567895", "484", "Dejan Djuric", LocalDate.parse("2022-06-01"));
		createCreditCardIfNotFound(7L, "1234561234567896", "485", "Sasa Lazarevic", LocalDate.parse("2022-07-01"));
		
		createMerchantIfNotFound("merchant1@gmail.com", "Petar Petrovic", 100000.0, "sodRrevLNjYm6F9WPUSRPxZWRPFSxz", "7Q5BPSmUCm29QSeW3YnAHSu9XpoOGVuPBSTxrQKmubjTdLdlPBBp3vccOcQ8so7WfATRdjH9BOiKvoG5SDTRQ0KNxQVBDHrCU7Pn", "RSD");
		createMerchantIfNotFound("merchant2@gmail.com", "Zarko Stefanovic", 100000.0, "XxzfKrwTEnj6hyRz11mVNpqHFbKivd", "rtHVfdEFMDc0nf3zb9MbxLGAodXL5mjmnm1BZrnPfHu8gMZo5T3uFcF1OkGveMblqYxaj4NBDUq5OTqrFYgqCXr3FwElpPJYJRWb", "USD");
		createMerchantIfNotFound("merchant3@gmail.com", "Aleksa Uskokovic", 100000.0, "maWaO83t4DdNlTXxUCDkBmZjgu1e3U", "JNncaxJvOF71OMRJaDMunLJf9b3HvhJvecTePQva7OExYPmPwzZR6i90YmOzUPJCxryppwoAQ0530PvRixdv0PYW9VAQmGZVOPvD", "EUR");
		createMerchantIfNotFound("merchant4@gmail.com", "Nikola Ivanovic", 100000.0, "aZY4L3Op9QLgxaEASok6fQ5Jwc7m7H", "fm670PAvkGy7UuR3HOUwMKRsBwWIlNbSHeETfRWmHgXn2xZGoofxg6Mq3o4902LzfKJmTqttUkDZ3T2S0U3bLFkaHdUbrfPfFwQr", "GBP");
		createMerchantIfNotFound("merchant5@gmail.com", "Ognjen Dobric", 100000.0, "KR9yZ3pOPwXfPHGycNvHeeuWvOQkGx", "qPqeu2S9f6EgU9Wdxy0DiwL6RkXxMpu4qTXMTx9OB6V3e5Idp1gmogRDBwfWpV00zXZ9q2tfRo0NspVjdyAuw3QLOlt7yY6Mjqdk", "RSD");
		createMerchantIfNotFound("merchant6@gmail.com", "Stefan Markovic", 100000.0, "9TwfdCupyJvK0j3ydoWRzbHHLWAeqP", "UGsu4bbILffOr41LiNB0E4LXCDBLDnw80HgX0lomDTr5Q4Qz34N95lXTc4QKyzZ7j72nHk3u1I7tKREvVrViNT3luztFfUrjcqTo", "RSD");

		alreadySetup = true;
	}

	@Transactional
	public void createCreditCardIfNotFound(Long id, String pan, String securityCode, String cardHolderName, LocalDate expirationDate) {
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

	@Transactional
	public void createMerchantIfNotFound(String email, String name, Double balance, String merchantId, String merchantPassword, String currency) {
		Merchant merchant = merchantRepo.findByEmail(name);
		if (merchant == null) {
			merchant = new Merchant();
			merchant.setName(name);
			merchant.setEmail(email);
			merchant.setBalance(balance);
			merchant.setMerchantId(merchantId);
			merchant.setMerchantPassword(merchantPassword);
			merchant.setCurrency(currency);
			merchant.setPan("1234567898765432");
		}
		merchantRepo.save(merchant);
	}

}
