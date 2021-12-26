package tim13.bank.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim13.bank.bank.model.Payment;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Long> {

}
