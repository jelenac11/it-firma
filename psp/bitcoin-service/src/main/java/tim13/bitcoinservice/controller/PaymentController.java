package tim13.bitcoinservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim13.bitcoinservice.dto.PaymentCallbackDTO;
import tim13.bitcoinservice.dto.PaymentDataDTO;
import tim13.bitcoinservice.service.PaymentService;

@RestController
@RequestMapping(value = "/api/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping(value = "/pay")
	public ResponseEntity<String> pay(@Valid @RequestBody PaymentDataDTO paymentDataDto) {
		try {
			return ResponseEntity.ok(paymentService.pay(paymentDataDto));
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/callback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> callback(@ModelAttribute PaymentCallbackDTO callbackDTO) {
		System.out.println(callbackDTO.toString());
		return new ResponseEntity<>(HttpStatus.OK);
	}

}