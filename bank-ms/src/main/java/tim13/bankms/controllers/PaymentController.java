package tim13.bankms.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim13.bankms.dto.PaymentDataDTO;
import tim13.bankms.services.PaymentService;

@RestController
@RequestMapping(value = "/api/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@PostMapping(value = "/pay")
	public ResponseEntity<String> pay(@RequestBody PaymentDataDTO paymentDataDto) {
		logger.trace("Payment requested.");
		return ResponseEntity.ok(paymentService.pay(paymentDataDto));
	}

}