package com.firma.psp.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firma.psp.dto.OrderDataDTO;
import com.firma.psp.services.OrderDataService;

@RestController
@RequestMapping(value = "/api/order-data", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://localhost:8089", maxAge = 3600, allowedHeaders = "*")
public class OrderDataController {

	@Autowired
	private OrderDataService orderDataService;

	private static final Logger logger = LoggerFactory.getLogger(OrderDataController.class);

	@PostMapping
	public ResponseEntity<?> addNewOrderData(@Valid @RequestBody OrderDataDTO orderDataDTO) {
		try {
			logger.trace("Order data addition requested.");
			return new ResponseEntity<>(orderDataService.addNewOrderData(orderDataDTO), HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
