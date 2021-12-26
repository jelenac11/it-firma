package tim13.bank.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim13.bank.bank.model.Merchant;

@Repository
public interface IMerchantRepository  extends JpaRepository<Merchant, Long>{

	Merchant findByEmail(String name);

	Merchant findByMerchantIdAndMerchantPassword(String merchantId, String merchantPassword);

}
