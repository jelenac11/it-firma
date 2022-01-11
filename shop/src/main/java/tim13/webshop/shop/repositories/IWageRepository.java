package tim13.webshop.shop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim13.webshop.shop.enums.WageStatus;
import tim13.webshop.shop.model.User;
import tim13.webshop.shop.model.Wage;

public interface IWageRepository extends JpaRepository<Wage, Long> {

	List<Wage> findByOwnerAndStatus(User owner, WageStatus status);

}
