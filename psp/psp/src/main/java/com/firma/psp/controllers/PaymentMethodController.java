package com.firma.psp.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firma.psp.dto.ChosenPaymentMethodsDTO;
import com.firma.psp.dto.NewPaymentMethodDTO;
import com.firma.psp.dto.PaymentRequestDTO;
import com.firma.psp.exceptions.RequestException;
import com.firma.psp.services.PaymentMethodService;

@RestController
@RequestMapping(value = "/api/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://localhost:8096", maxAge = 3600, allowedHeaders = "*")
public class PaymentMethodController {

	@Autowired
	private PaymentMethodService methodService;

	private static final Logger logger = LoggerFactory.getLogger(PaymentMethodController.class);

	@GetMapping
	public ResponseEntity<?> getAll() {
		try {
			logger.trace("All payment methods requested.");
			return new ResponseEntity<>(methodService.getAll(), HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/chosen")
	public ResponseEntity<?> addSupportedMethods(@Valid @RequestBody ChosenPaymentMethodsDTO methods) {
		try {
			logger.trace("Choosing merchant supported payment methods requested.");
			methodService.addSupportedMethods(methods);
			return new ResponseEntity<>("Successfully added methods!", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/merchant")
	public ResponseEntity<?> getMethods() {
		try {
			logger.trace("Merchant supported payment methods requested.");
			return new ResponseEntity<>(methodService.getMethods(), HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<?> addNewMethod(@Valid @RequestBody NewPaymentMethodDTO newMethod) {
		try {
			logger.trace("Payment method addition requested.");
			methodService.save(newMethod);
			return new ResponseEntity<>("Successfully added new payment method!", HttpStatus.CREATED);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/get-payment-url")
	public ResponseEntity<?> getPaymentUrl(@Valid @RequestBody PaymentRequestDTO paymentRequest) {
		try {
			logger.trace("Payment requested.");
			return new ResponseEntity<>(methodService.getPaymentUrl(paymentRequest), HttpStatus.OK);
		} catch (RequestException e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/get-payment-url-for-wage")
	public ResponseEntity<?> getPaymentUrlForWage(@Valid @RequestBody PaymentRequestDTO paymentRequest) {
		try {
			logger.trace("Payment requested.");
			return new ResponseEntity<>(methodService.getPaymentUrlForWage(paymentRequest), HttpStatus.OK);
		} catch (RequestException e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
