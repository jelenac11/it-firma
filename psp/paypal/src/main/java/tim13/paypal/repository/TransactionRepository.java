package tim13.paypal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim13.paypal.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
