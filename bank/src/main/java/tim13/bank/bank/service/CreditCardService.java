package tim13.bank.bank.service;

import tim13.bank.bank.dto.CardDetailsDTO;
import tim13.bank.bank.exceptions.RequestException;
import tim13.bank.bank.model.CreditCard;
import tim13.bank.bank.repository.ICreditCardRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public class CreditCardService {

	@Autowired
	private ICreditCardRepository cardRepo;

	@Value("${bank_identification_number}")
	private String identificationNumber;

	private static final Logger logger = LoggerFactory.getLogger(CreditCardService.class);
	
	public boolean isClientOfBank(String pan) {
		logger.info("Checking if client and merchant bank are same");
		String bankIdentificationNumber = pan.substring(0, 6);
		return bankIdentificationNumber.equals(identificationNumber);
	}

	public CreditCard checkCardDetails(@Valid CardDetailsDTO cardDetailsDTO)
			throws NotFoundException, RequestException {
		CreditCard card = cardRepo.findByPanAndSecurityCodeAndCardHolderName(cardDetailsDTO.getPAN(),
				cardDetailsDTO.getSecurityCode(), cardDetailsDTO.getCardHolderName());
		logger.info("Checking credit card data");
		if (card == null) {
			logger.info("Nonexisting card");
			throw new NotFoundException("Credit card doesnt exist");
		}
		if (!checkExpirationData(card.getExpirationDate(), cardDetailsDTO.getExpirationDate())) {
			logger.info("Credit card expired");
			throw new RequestException("Invalid credit card data.");
		}
		return card;
	}

	private boolean checkExpirationData(LocalDate expirationDate, String expiration) {
		logger.info("Checking expiration data of credit card");
		String[] values = expiration.split("/");
		int month = Integer.parseInt(values[0]);

		if (month != expirationDate.getMonthValue()) {
			return false;
		}
		String yearFromDatabase = "";
		if (values[1].length() == 2) {
			yearFromDatabase = Integer.toString(expirationDate.getYear()).substring(2);
		} else {
			yearFromDatabase = Integer.toString(expirationDate.getYear());
		}

		if (!values[1].equals(yearFromDatabase)) {
			return false;
		}
		LocalDateTime currentDateAndTime = LocalDateTime.now();
		if (expirationDate.getYear() < currentDateAndTime.getYear()) {
			return false;
		}
		if (expirationDate.getYear() == currentDateAndTime.getYear()) {
			return expirationDate.getMonthValue() >= currentDateAndTime.getMonthValue();
		}
		return true;
	}
}
