package tim13.bank.bank.service;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.bank.bank.dto.MerchantDTO;
import tim13.bank.bank.model.Merchant;
import tim13.bank.bank.repository.IMerchantRepository;

@Service
public class MerchantService {

	@Autowired
	public IMerchantRepository merchantRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(MerchantService.class);
	
	public Merchant save(MerchantDTO merchantDTO) {
		logger.info("Creating new merchant bank account");
		String id = RandomStringUtils.random(30, true, true);
		String password = RandomStringUtils.random(100, true, true);
		Merchant merchant = new Merchant();
		merchant.setBalance(merchantDTO.getBalance());
		merchant.setCurrency(merchantDTO.getCurrency());
		merchant.setEmail(merchantDTO.getEmail());
		merchant.setMerchantId(id);
		merchant.setMerchantPassword(password);
		merchant.setName(merchantDTO.getName());
		merchantRepo.save(merchant);
		return merchant;
	}


	public void transferMoneyToMerchant(String merchantId, double merchantAmount) {
		logger.info("Transfering money to merchant bank account");
		Merchant merchant = merchantRepo.getByMerchantId(merchantId);
        merchant.setBalance(merchant.getBalance() + merchantAmount);
        merchantRepo.save(merchant);
    }

}
