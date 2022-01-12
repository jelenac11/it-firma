package tim13.webshop.shop.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tim13.webshop.shop.services.TransactionService;

@RestController
@RequestMapping(value = "/api/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://79eb-87-116-162-55.ngrok.io", maxAge = 3600, allowedHeaders = "*")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestParam Integer status) {
		logger.trace("Transaction update requested.");
		try {
			return new ResponseEntity<>(transactionService.update(id, status), HttpStatus.OK);
		} catch (MailException | InterruptedException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Email service not working.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
