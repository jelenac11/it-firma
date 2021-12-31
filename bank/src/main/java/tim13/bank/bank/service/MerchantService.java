package tim13.bank.bank.service;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.bank.bank.dto.MerchantDTO;
import tim13.bank.bank.model.Merchant;
import tim13.bank.bank.repository.IMerchantRepository;

@Service
public class MerchantService {

	@Autowired
	public IMerchantRepository merchantRepo;
	
	
	public Merchant save(MerchantDTO merchantDTO) {
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
		Merchant merchant = merchantRepo.getByMerchantId(merchantId);
        merchant.setBalance(merchant.getBalance() + merchantAmount);
        merchantRepo.save(merchant);
    }

}
