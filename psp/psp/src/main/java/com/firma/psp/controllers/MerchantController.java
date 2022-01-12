package com.firma.psp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firma.psp.services.MerchantService;

@RestController
@RequestMapping(value = "/api/merchants", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://localhost:8096", maxAge = 3600, allowedHeaders = "*")
public class MerchantController {

	@Autowired
	private MerchantService merchantService;

	private static final Logger logger = LoggerFactory.getLogger(MerchantController.class);

	@GetMapping(value = "supported-methods/{id}")
	public ResponseEntity<?> getMerchantMethods(@PathVariable("id") Long id) {
		try {
			logger.trace("Merchant supported payment methods requested.");
			return new ResponseEntity<>(merchantService.getMerchantMethods(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
