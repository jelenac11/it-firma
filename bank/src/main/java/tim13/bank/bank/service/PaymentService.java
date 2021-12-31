package tim13.bank.bank.service;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import tim13.bank.bank.constants.BankConstants;
import tim13.bank.bank.dto.CardDetailsDTO;
import tim13.bank.bank.dto.PCCRequestDTO;
import tim13.bank.bank.dto.PaymentRequestDTO;
import tim13.bank.bank.exceptions.RequestException;
import tim13.bank.bank.model.CreditCard;
import tim13.bank.bank.model.Merchant;
import tim13.bank.bank.model.Payment;
import tim13.bank.bank.model.Transaction;
import tim13.bank.bank.model.TransactionStatus;
import tim13.bank.bank.repository.IMerchantRepository;
import tim13.bank.bank.repository.IPaymentRepository;
import tim13.bank.bank.repository.ITransactionRepository;

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
	@Autowired
	private ITransactionRepository transactionRepository;

	public String pay(PaymentRequestDTO paymentRequestDto) {
		if (!checkMerchantData(paymentRequestDto.getMerchantId(), paymentRequestDto.getMerchantPassword())) {
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
		return BankConstants.FRONTEND_PAYMENT + created.getId();
	}

	private boolean checkMerchantData(String merchantId, String merchantPassword) {
		Merchant m = merchantRepo.findByMerchantIdAndMerchantPassword(merchantId, merchantPassword);
		if (m == null) {
			return false;
		}
		return true;
	}

	public String confirmPayment(Long id, @Valid CardDetailsDTO cardDetailsDTO)
			throws NotFoundException, RequestException {
		Payment payment = paymentRepo.getOne(id);
		if (payment == null) {
			throw new NotFoundException("Payment does not exist");
		}

		Transaction transaction = null;
		Merchant merchant = merchantRepo.getByMerchantId(payment.getMerchantId());
		if (cardService.isClientOfBank(cardDetailsDTO.getPAN())) {
			CreditCard card = cardService.checkCardDetails(cardDetailsDTO);
			transaction = transactionService.transferSameBank(card, merchant, payment);
		} else {
			transaction = transactionService.transferDifferentBanks(payment, merchant, cardDetailsDTO);
		}

		transactionRepository.save(transaction);
		if (transaction.getStatus().equals(TransactionStatus.SUCCESS)) {
			return payment.getSuccessUrl() + "/" + payment.getMerchantOrderId();
		} else if (transaction.getStatus().equals(TransactionStatus.FAILED)) {
			return payment.getFailedUrl() + "/" + payment.getMerchantOrderId();
		} else {
			return payment.getErrorUrl() + "/" + payment.getMerchantOrderId();
		}
	}

}
