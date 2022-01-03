package tim13.webshop.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tim13.webshop.shop.model.Subscription;

public interface ISubscriptionRepository extends JpaRepository<Subscription, Long> {

}
