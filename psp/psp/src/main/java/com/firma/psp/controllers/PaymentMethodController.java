package com.firma.psp.controllers;

import javax.validation.Valid;

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
import com.firma.psp.services.PaymentMethodService;

@RestController
@RequestMapping(value = "/api/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8096", maxAge = 3600, allowedHeaders = "*")
public class PaymentMethodController {

	@Autowired
	private PaymentMethodService methodService;
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		try {
			return new ResponseEntity<>(methodService.getAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/chosen")
	public ResponseEntity<?> addSupportedMethods(@Valid @RequestBody ChosenPaymentMethodsDTO methods) {
		try {
			methodService.addSupportedMethods(methods);
			return new ResponseEntity<>("Successfully added methods!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/merchant")
	public ResponseEntity<?> getMethods() {
		try {
			return new ResponseEntity<>(methodService.getMethods(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> addNewMethod(@Valid @RequestBody NewPaymentMethodDTO newMethod) {
		try {
			methodService.save(newMethod);
			return new ResponseEntity<>("Successfully added new payment method!", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
