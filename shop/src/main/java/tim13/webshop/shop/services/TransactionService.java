package tim13.webshop.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.enums.TransactionStatus;
import tim13.webshop.shop.model.Transaction;
import tim13.webshop.shop.repositories.ITransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TransactionService {

	private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
	
	@Autowired
	private ITransactionRepository transactionRepository;

	public Transaction save(Transaction transaction) {
		Transaction newTransaction = transactionRepository.save(transaction);
		
		logger.info("New transaction created");
		
		return newTransaction;
	}

	public void update(Long id, int status) {
		Transaction transaction = transactionRepository.getOne(id);

		if (transaction == null) {
			return;
		}

		switch (status) {
		case 1:
			transaction.setStatus(TransactionStatus.COMPLETED);
			break;
		case 2:
			transaction.setStatus(TransactionStatus.FAILED);
			break;
		case 3:
			transaction.setStatus(TransactionStatus.CANCELLED);
			break;
		default:
			break;
		}

		transaction = transactionRepository.save(transaction);
		
		logger.info("Transaction with id " + transaction.getId() + " updated on status " + transaction.getStatus());
	}
}
