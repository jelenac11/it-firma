package tim13.bank.bank.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	private ITransactionRepository transactionRepo;

	@Autowired
	private AccountService accountService;

	@Autowired
	private MerchantService merchantService;

	public Transaction transferSameBank(CreditCard card, Merchant merchant, Payment payment) {
		Transaction transaction = createTransaction(payment, card.getPan());

		double buyerAmount = convertAmount(card.getAccount().getCurrency(), payment.getAmount());
		double merchantAmount = convertAmount(merchant.getCurrency(), payment.getAmount());

		try {
			transfer(card, merchant.getMerchantId(), buyerAmount, merchantAmount);
		} catch (IllegalArgumentException e) {
			transaction.setStatus(TransactionStatus.FAILED);
			return transaction;
		} catch (Exception e) {
			transaction.setStatus(TransactionStatus.ERROR);
			return transaction;
		}

		transaction.setStatus(TransactionStatus.SUCCESS);
		return transaction;
	}

	public Transaction transferDifferentBanks(Payment payment, Merchant merchant, CardDetailsDTO card)
			throws RequestException {
		Transaction transaction = createTransaction(payment, card.getPAN());

		Date d = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(d);
		PCCRequestDTO pccRequestDto = new PCCRequestDTO(transaction.getId(), Timestamp.valueOf(dateStr),
				payment.getAmount(), card.getPAN(), card.getCardHolderName(), card.getExpirationDate(),
				card.getSecurityCode());

		RestTemplate rs = new RestTemplate();
		ResponseEntity<PCCResponseDTO> response = rs.postForEntity("http://localhost:9003/api/payment/pay",
				pccRequestDto, PCCResponseDTO.class);
		if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
			throw new RequestException("Invalid credit card data.");
		}

		PCCResponseDTO pccResponse = response.getBody();
		transaction.setStatus(pccResponse.getStatus());

		if (transaction.getStatus().equals(TransactionStatus.SUCCESS)) {
			double merchantAmount = convertAmount(merchant.getCurrency(), payment.getAmount());
			merchantService.transferMoneyToMerchant(merchant.getMerchantId(), merchantAmount);
		}

		return transaction;
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

	private Transaction createTransaction(Payment paymentRequest, String pan) {
		return new Transaction(paymentRequest.getAmount(), paymentRequest.getMerchantId(),
				paymentRequest.getMerchantOrderId(), paymentRequest.getMerchantTimestamp(), pan,
				paymentRequest.getId());
	}

	private void transfer(CreditCard buyer, String merchantId, double buyerAmount, double merchantAmount)
			throws IllegalArgumentException {
		if (!accountService.hasEnoughMoney(buyerAmount, buyer)) {
			throw new IllegalArgumentException("You don't have enough money.");
		}
		merchantService.transferMoneyToMerchant(merchantId, merchantAmount);
	}

}
