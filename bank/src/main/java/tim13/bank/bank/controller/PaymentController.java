package tim13.bank.bank.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim13.bank.bank.dto.CardDetailsDTO;
import tim13.bank.bank.dto.PaymentRequestDTO;
import tim13.bank.bank.service.PaymentService;

@RestController
@CrossOrigin(origins = "http://localhost:8082", maxAge = 3600, allowedHeaders = "*")
@RequestMapping(value = "/api/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@PostMapping(value = "/pay")
	public ResponseEntity<?> pay(@Valid @RequestBody PaymentRequestDTO paymentRequestDto) {
		logger.trace("Payment requested.");
		try {
			return ResponseEntity.ok(paymentService.pay(paymentRequestDto));
		} catch (Exception e) {
			logger.trace(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/confirm/{id}")
	public ResponseEntity<?> pay(@PathVariable(value = "id") Long id,
			@Valid @RequestBody CardDetailsDTO cardDetailsDTO) {
		try {
			return new ResponseEntity<>(paymentService.confirmPayment(id, cardDetailsDTO), HttpStatus.OK);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
