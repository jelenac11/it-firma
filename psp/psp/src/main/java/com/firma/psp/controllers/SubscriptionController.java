package com.firma.psp.controllers;

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

import com.firma.psp.dto.CancellingSubscriptionDto;
import com.firma.psp.dto.PlanDto;
import com.firma.psp.dto.SubscribeDto;
import com.firma.psp.exceptions.BaseException;
import com.firma.psp.services.SubscriptionService;

@RestController
@RequestMapping(value = "/api/subscription", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://localhost:8096", maxAge = 3600, allowedHeaders = "*")
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;

	private static final Logger logger = LoggerFactory.getLogger(PaymentMethodController.class);
	
	@PostMapping(value = "/create-plan")
	public ResponseEntity<?> createPlan(@RequestBody PlanDto planDto) {
		try {
			String productId = subscriptionService.createPlan(planDto);
			logger.info("Subscription plan created");
			return ResponseEntity.status(HttpStatus.CREATED).body(productId);
		} catch (BaseException e) {
			logger.trace(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), e.getStatus());
		}
	}

	@PostMapping(value = "/subscribe")
	public ResponseEntity<?> subscribe(@RequestBody SubscribeDto subscribeDto) {
		logger.info("Subcribe requested");
		try {
			String productId = subscriptionService.subscribe(subscribeDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(productId);
		} catch (BaseException e) {
			logger.trace(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), e.getStatus());
		}
	}

	@PostMapping(value = "/unsubscribe/{id}")
	public ResponseEntity<?> unsubscribe(@PathVariable("id") String subscriptionId,
			@RequestBody CancellingSubscriptionDto dto) {
		logger.info("Unsubscribe requested!");
		try {
			subscriptionService.unsubscribe(subscriptionId, dto);

			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (BaseException e) {
			logger.trace(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), e.getStatus());
		}
	}
}
