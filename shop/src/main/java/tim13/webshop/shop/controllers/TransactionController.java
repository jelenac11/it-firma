package tim13.webshop.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tim13.webshop.shop.services.TransactionService;

@RestController
@RequestMapping(value = "/api/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8096", maxAge = 3600, allowedHeaders = "*")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PutMapping(value = "{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestParam Integer status) {
		transactionService.update(id, status);

		return new ResponseEntity<>("Transaction successfully updated", HttpStatus.NO_CONTENT);
	}
}
