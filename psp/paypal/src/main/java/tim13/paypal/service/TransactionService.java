package tim13.paypal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.paypal.model.Transaction;
import tim13.paypal.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	public Transaction save(Transaction transaction) {
		return this.transactionRepository.save(transaction);
	}

}
