package tim13.bank.bank.service;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import tim13.bank.bank.constants.BankConstants;
import tim13.bank.bank.dto.CardDetailsDTO;
import tim13.bank.bank.dto.PaymentRequestDTO;
import tim13.bank.bank.exceptions.RequestException;
import tim13.bank.bank.model.CreditCard;
import tim13.bank.bank.model.Merchant;
import tim13.bank.bank.model.Payment;
import tim13.bank.bank.model.Transaction;
import tim13.bank.bank.model.TransactionStatus;
import tim13.bank.bank.repository.IMerchantRepository;
import tim13.bank.bank.repository.IPaymentRepository;

@Service
public class PaymentService {

	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

	@Autowired
	private IPaymentRepository paymentRepo;
	@Autowired
	private IMerchantRepository merchantRepo;
	@Autowired
	private CreditCardService cardService;
	@Autowired
	private TransactionService transactionService;

	public String pay(PaymentRequestDTO paymentRequestDto) {
		logger.info("Paying requested");
		if (!checkMerchantData(paymentRequestDto.getMerchantId(), paymentRequestDto.getMerchantPassword())) {
			logger.debug("Invalid merchant data");
			return paymentRequestDto.getErrorUrl();
		}
		Payment p = new Payment();
		p.setAmount(paymentRequestDto.getAmount());
		p.setErrorUrl(paymentRequestDto.getErrorUrl());
		p.setFailedUrl(paymentRequestDto.getCancelUrl());
		p.setMerchantId(paymentRequestDto.getMerchantId());
		p.setMerchantTimestamp(paymentRequestDto.getTimestamp());
		p.setMerchantOrderId(paymentRequestDto.getMerchantOrderId());
		p.setSuccessUrl(paymentRequestDto.getSuccessUrl());
		Payment created = paymentRepo.save(p);
		logger.info("Creating new payment with id " + created.getId());
		return BankConstants.FRONTEND_PAYMENT + created.getId();
	}

	private boolean checkMerchantData(String merchantId, String merchantPassword) {
		logger.info("Checking merchant data");
		Merchant m = merchantRepo.findByMerchantIdAndMerchantPassword(merchantId, merchantPassword);
		if (m == null) {
			return false;
		}
		return true;
	}

	public String confirmPayment(Long id, @Valid CardDetailsDTO cardDetailsDTO)
			throws NotFoundException, RequestException {
		logger.info("Confirming payment with id " + id);
		Payment payment = paymentRepo.getOne(id);
		if (payment == null) {
			logger.debug("Requested payment doesnt exist");
			throw new NotFoundException("Payment does not exist");
		}

		Transaction transaction = null;
		Merchant merchant = merchantRepo.getByMerchantId(payment.getMerchantId());
		if (cardService.isClientOfBank(cardDetailsDTO.getPAN())) {
			logger.info("Client has account in same bank as merchant");
			CreditCard card = cardService.checkCardDetails(cardDetailsDTO);
			transaction = transactionService.transferSameBank(card, merchant, payment);
		} else {
			logger.info("Client has account in different bank than merchant");
			transaction = transactionService.transferDifferentBanks(payment, merchant, cardDetailsDTO);
		}

		if (transaction.getStatus().equals(TransactionStatus.SUCCESS)) {
			logger.info("Transaction with id " + transaction.getId() + " was successfull");
			return payment.getSuccessUrl() + "/" + payment.getMerchantOrderId();
		} else if (transaction.getStatus().equals(TransactionStatus.FAILED)) {
			logger.info("Transaction " + transaction.getId() + " failed");
			return payment.getFailedUrl() + "/" + payment.getMerchantOrderId();
		} else {
			logger.info("Error in transaction");
			return payment.getErrorUrl() + "/" + payment.getMerchantOrderId();
		}
	}

}
