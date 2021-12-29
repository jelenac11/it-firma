package tim13.paypal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim13.paypal.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	Subscription findBySubscriptionId(String subscriptionId);

}
