package tim13.paypal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim13.paypal.model.PaymentRequest;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {

	PaymentRequest findOneByPaymentId(String paymentId);

}
