package tim13.bank2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim13.bank2.model.Transaction;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {

}
