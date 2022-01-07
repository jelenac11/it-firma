package tim13.bank2.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;
import tim13.bank2.dto.PCCRequestDTO;
import tim13.bank2.service.PaymentService;

@RestController
@RequestMapping(value = "/api/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@PostMapping(value = "/pay")
	public ResponseEntity<?> pay(@Valid @RequestBody PCCRequestDTO pccRequestDto) {
		logger.trace("Payment requested.");
		try {
			return ResponseEntity.ok(paymentService.pay(pccRequestDto));
		} catch (NotFoundException e) {
			logger.trace(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
