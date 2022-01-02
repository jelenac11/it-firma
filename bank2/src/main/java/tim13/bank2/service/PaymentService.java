package tim13.bank2.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import tim13.bank2.constants.BankConstants;
import tim13.bank2.dto.PCCRequestDTO;
import tim13.bank2.dto.PCCResponseDTO;
import tim13.bank2.model.CreditCard;
import tim13.bank2.model.Transaction;
import tim13.bank2.model.TransactionStatus;
import tim13.bank2.repository.ICreditCardRepository;
import tim13.bank2.repository.ITransactionRepository;

@Service
public class PaymentService {

	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

	@Autowired
	private ICreditCardRepository cardRepo;

	@Autowired
	private ITransactionRepository transactionRepo;

	@Autowired
	private AccountService accountService;

	public PCCResponseDTO pay(@Valid PCCRequestDTO pccRequestDto) throws NotFoundException {
		CreditCard cc = cardRepo.findByPanAndSecurityCodeAndCardHolderName(pccRequestDto.getPAN(),
				pccRequestDto.getSecurityCode(), pccRequestDto.getCardHolderName());
		if (cc == null) {
			throw new NotFoundException("Credit card doesnt exist");
		}
		if (!checkExpirationData(cc.getExpirationDate(), pccRequestDto.getExpirationDate())) {
			throw new NotFoundException("Invalid credit card data.");
		}
		Transaction transaction = createTransaction(pccRequestDto);

		double buyerAmount = convertAmount(cc.getAccount().getCurrency(), pccRequestDto.getAmount());

		try {
			transfer(cc, buyerAmount);
		} catch (IllegalArgumentException e) {
			transaction.setStatus(TransactionStatus.FAILED);
		} catch (Exception e) {
			transaction.setStatus(TransactionStatus.ERROR);
		}
		transaction.setStatus(TransactionStatus.SUCCESS);

		Transaction t = transactionRepo.save(transaction);

		return new PCCResponseDTO(pccRequestDto.getAcquirerOrderId(), t.getId(), pccRequestDto.getAcquirerTimestamp(),
				t.getIssuerTimestamp(), t.getStatus());
	}

	private double convertAmount(String currency, Double amount) {
		if (currency.equals("RSD")) {
			return amount * BankConstants.USD_RSD;
		} else if (currency.equals("EUR")) {
			return amount * BankConstants.USD_EUR;
		} else if (currency.equals("GBP")) {
			return amount * BankConstants.USD_GBP;
		}
		return amount;
	}

	private Transaction createTransaction(PCCRequestDTO pcc) {
		Date d = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(d);
		return new Transaction(pcc.getAmount(), pcc.getAcquirerOrderId(), Timestamp.valueOf(dateStr), pcc.getPAN());
	}

	private void transfer(CreditCard buyer, double buyerAmount) throws IllegalArgumentException {
		if (!accountService.hasEnoughMoney(buyerAmount, buyer)) {
			throw new IllegalArgumentException("You don't have enough money.");
		}
	}

	private boolean checkExpirationData(LocalDate expirationDate, String expiration) {
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
