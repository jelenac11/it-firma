package tim13.bank2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim13.bank2.model.CreditCard;

@Repository
public interface ICreditCardRepository extends JpaRepository<CreditCard, Long> {

	CreditCard findByAccountId(Long id);

	CreditCard findByPanAndSecurityCodeAndCardHolderName(String pan, String securityCode, String cardHolderName);

}
