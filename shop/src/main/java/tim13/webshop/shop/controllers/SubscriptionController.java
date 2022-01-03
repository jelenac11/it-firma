package tim13.webshop.shop.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim13.webshop.shop.dto.UnsubscribeDTO;
import tim13.webshop.shop.exceptions.BaseException;
import tim13.webshop.shop.services.SubscriptionService;

@RestController
@RequestMapping(value = "/api/subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowedHeaders = "*")
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;

	private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

	@GetMapping(value = "/subscribe/{planId}")
	public ResponseEntity<?> subscribe(@PathVariable(value = "planId") Long planId) {
		try {
			logger.trace("Requested buying plan");

			return new ResponseEntity<String>(subscriptionService.subscribe(planId), HttpStatus.CREATED);
		} catch (BaseException e) {
			return new ResponseEntity<>(e.getMessage(), e.getStatus());
		}
	}

	@GetMapping(value = "/{subscriptionId}/{transactionId}")
	public ResponseEntity<?> giveSubscriptionToUserAfterBuying(
			@PathVariable(value = "subscriptionId") String subscriptionId,
			@PathVariable(value = "transactionId") Long transactionId) {
		logger.trace("Requested creating subscription");

		try {
			subscriptionService.createSubscription(transactionId, subscriptionId);

			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (BaseException e) {
			return new ResponseEntity<>(e.getMessage(), e.getStatus());
		}
	}

	@PostMapping(value = "/unsubscribe/{id}")
	public ResponseEntity<?> unsubscribe(@PathVariable("id") Long subscriptionId, @RequestBody UnsubscribeDTO dto) {
		logger.trace("Requested canceling subscription");

		try {
			subscriptionService.unsubscribe(subscriptionId, dto);

			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (BaseException e) {
			return new ResponseEntity<>(e.getMessage(), e.getStatus());
		}
	}
}
