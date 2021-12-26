package tim13.bank.bank.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.bank.bank.constants.BankConstants;
import tim13.bank.bank.dto.PaymentRequestDTO;
import tim13.bank.bank.model.Merchant;
import tim13.bank.bank.model.Payment;
import tim13.bank.bank.repository.IMerchantRepository;
import tim13.bank.bank.repository.IPaymentRepository;

@Service
public class PaymentService {

	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

	@Autowired
	private IPaymentRepository paymentRepo;
	@Autowired
	private IMerchantRepository merchantRepo;
	
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

}
