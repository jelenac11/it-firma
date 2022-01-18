package tim13.bank.bank.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tim13.bank.bank.constants.BankConstants;
import tim13.bank.bank.dto.CardDetailsDTO;
import tim13.bank.bank.dto.PCCRequestDTO;
import tim13.bank.bank.dto.PCCResponseDTO;
import tim13.bank.bank.exceptions.RequestException;
import tim13.bank.bank.model.CreditCard;
import tim13.bank.bank.model.Merchant;
import tim13.bank.bank.model.Payment;
import tim13.bank.bank.model.Transaction;
import tim13.bank.bank.model.TransactionStatus;
import tim13.bank.bank.repository.ITransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private AccountService accountService;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private ITransactionRepository transactionRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

	public Transaction transferSameBank(CreditCard card, Merchant merchant, Payment payment) {
		Transaction transaction = createTransaction(payment, card.getPan());

		double buyerAmount = convertAmount(card.getAccount().getCurrency(), payment.getAmount());
		double merchantAmount = convertAmount(merchant.getCurrency(), payment.getAmount());

		try {
			logger.info("Transfering money in same bank");
			transfer(card, merchant.getMerchantId(), buyerAmount, merchantAmount);
		} catch (IllegalArgumentException e) {
			logger.debug(e.getMessage());
			transaction.setStatus(TransactionStatus.FAILED);
			return transaction;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			transaction.setStatus(TransactionStatus.ERROR);
			return transaction;
		}

		logger.info("Successfull transaction with id " + transaction.getId());
		transaction.setStatus(TransactionStatus.SUCCESS);
		transaction = transactionRepository.save(transaction);
		return transaction;
	}

	public Transaction transferDifferentBanks(Payment payment, Merchant merchant, CardDetailsDTO card)
			throws RequestException {
		logger.info("Transfering money between different banks");
		Transaction transaction = createTransaction(payment, card.getPAN());
		transactionRepository.save(transaction);

		Date d = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(d);
		PCCRequestDTO pccRequestDto = new PCCRequestDTO(transaction.getId(), Timestamp.valueOf(dateStr),
				payment.getAmount(), card.getPAN(), card.getCardHolderName(), card.getExpirationDate(),
				card.getSecurityCode());

		RestTemplate rs = new RestTemplate();
		ResponseEntity<PCCResponseDTO> response = rs.postForEntity("https://localhost:9003/api/payment/pay",
				pccRequestDto, PCCResponseDTO.class);
		if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
			logger.debug("Invalid credit card data");
			throw new RequestException("Invalid credit card data.");
		}

		PCCResponseDTO pccResponse = response.getBody();
		transaction.setStatus(pccResponse.getStatus());
		transactionRepository.save(transaction);

		if (transaction.getStatus().equals(TransactionStatus.SUCCESS)) {
			logger.info("Successfull transaction with id " + transaction.getId());
			double merchantAmount = convertAmount(merchant.getCurrency(), payment.getAmount());
			merchantService.transferMoneyToMerchant(merchant.getMerchantId(), merchantAmount);
		}

		return transaction;
	}

	private double convertAmount(String currency, Double amount) {
		logger.info("Converting money");
		if (currency.equals("RSD")) {
			return amount * BankConstants.USD_RSD;
		} else if (currency.equals("EUR")) {
			return amount * BankConstants.USD_EUR;
		} else if (currency.equals("GBP")) {
			return amount * BankConstants.USD_GBP;
		}
		return amount;
	}

	private Transaction createTransaction(Payment paymentRequest, String pan) {
		logger.info("Creating new transaction for payment with id " + paymentRequest.getId());
		return new Transaction(paymentRequest.getAmount(), paymentRequest.getMerchantId(),
				paymentRequest.getMerchantOrderId(), paymentRequest.getMerchantTimestamp(), pan,
				paymentRequest.getId());
	}

	private void transfer(CreditCard buyer, String merchantId, double buyerAmount, double merchantAmount)
			throws IllegalArgumentException {
		if (!accountService.hasEnoughMoney(buyerAmount, buyer)) {
			logger.info("Not enough money on bank account with pan **** **** **** " + buyer.getPan().substring(12));
			throw new IllegalArgumentException("You don't have enough money.");
		}
		logger.info("Enough money on bank account with pan **** **** **** " + buyer.getPan().substring(12));
		merchantService.transferMoneyToMerchant(merchantId, merchantAmount);
	}

}
