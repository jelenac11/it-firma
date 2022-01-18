package tim13.paypal.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tim13.paypal.dto.PaymentRequestDto;
import tim13.paypal.dto.WageDTO;
import tim13.paypal.exceptions.BaseException;
import tim13.paypal.mapper.PaymentRequestMapper;
import tim13.paypal.mapper.WageMapper;
import tim13.paypal.service.PaymentService;

@RestController
@RequestMapping(value = "/api/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private PaymentRequestMapper paymentRequestMapper;

	@Autowired
	private WageMapper wageMapper;

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@PostMapping(value = "/pay")
	public ResponseEntity<String> createUrl(@RequestBody PaymentRequestDto paymentRequestDto) {
		logger.trace("URL creation requested.");

		return ResponseEntity.ok(paymentService.createUrl(paymentRequestMapper.toEntity(paymentRequestDto)));
	}

	@PostMapping(value = "/pay/wage")
	public ResponseEntity<String> payWage(@RequestBody WageDTO wageDto) {
		logger.trace("Paying wage requested.");

		return ResponseEntity.ok(paymentService.payWage(wageMapper.toEntity(wageDto)));
	}

	@GetMapping(value = "/execute", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> executePayment(@RequestParam("paymentId") String paymentId,
			@RequestParam("PayerID") String payerId) {
		logger.trace("Payment execution requested.");

		try {
			return ResponseEntity.status(HttpStatus.SEE_OTHER)
					.location(URI.create(paymentService.executePayment(payerId, paymentId))).build();
		} catch (BaseException e) {
			return new ResponseEntity<>(e.getMessage(), e.getStatus());
		}
	}

}