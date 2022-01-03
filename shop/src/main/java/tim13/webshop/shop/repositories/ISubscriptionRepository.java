package tim13.webshop.shop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim13.webshop.shop.model.Subscription;
import tim13.webshop.shop.model.User;

public interface ISubscriptionRepository extends JpaRepository<Subscription, Long> {

	List<Subscription> findByBuyer(User buyer);
}
