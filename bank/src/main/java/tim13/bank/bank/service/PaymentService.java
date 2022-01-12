package tim13.bank.bank.service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

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

	public String payQR(Long id) throws IOException, WriterException, NotFoundException {
		logger.info("Creating QR code for mobile payment.");

		String fileName = id + ".png";

		Payment payment = paymentRepo.getOne(id);
		if (payment == null) {
			logger.debug("Requested payment doesn't exist");

			throw new NotFoundException("Payment doesn't exist");
		}

		Merchant merchant = merchantRepo.getByMerchantId(payment.getMerchantId());

		JSONObject planRequestData = createJSONForQR(payment, merchant);

		createQRCodeAndWriteInFile(planRequestData.toString(), fileName);

		return "/" + fileName;
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

	private void createQRCodeAndWriteInFile(String data, String fileName) throws WriterException, IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();

		logger.info("Creating QR code.");
		BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 350, 350);

		Path path = FileSystems.getDefault().getPath("./src/main/resources/static/" + fileName);

		logger.info("Writing image in file system.");
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
	}

	private JSONObject createJSONForQR(Payment payment, Merchant merchant) {
		JSONObject qr = new JSONObject();

		qr.put("acquirer", merchant.getName());
		qr.put("acquirerAccount", merchant.getPan());
		qr.put("currency", merchant.getCurrency());
		qr.put("successUrl", payment.getSuccessUrl());
		qr.put("errorUrl", payment.getErrorUrl());
		qr.put("failedUrl", payment.getFailedUrl());
		qr.put("amount", convertAmount(merchant.getCurrency(), payment.getAmount()));

		return qr;
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

}
