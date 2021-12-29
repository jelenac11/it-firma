package tim13.paypal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim13.paypal.dto.CancellingSubscriptionDto;
import tim13.paypal.dto.PlanDto;
import tim13.paypal.mapper.PlanMapper;
import tim13.paypal.mapper.ProductMapper;
import tim13.paypal.model.Plan;
import tim13.paypal.model.Product;
import tim13.paypal.service.SubscriptionService;

@RestController
@RequestMapping(value = "/api/subscription", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private PlanMapper planMapper;

	private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

	@GetMapping(value = "/subscribe/{planId}")
	public ResponseEntity<?> createSubscribeUrl(@PathVariable("planId") String planId) {
		logger.info("URL creation for subscribe requested.");

		return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionService.subscribe(planId));
	}

	@PostMapping(value = "/unsubscribe/{id}")
	public ResponseEntity<?> unsubscribe(@PathVariable("id") String subscriptionId,
			@RequestBody CancellingSubscriptionDto dto) {
		logger.info("Unsubscribe requested.");

		subscriptionService.unsubscribe(subscriptionId, dto);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> createPlan(@RequestBody PlanDto planDto) {
		logger.info("Plan creation requested.");

		Plan plan = planMapper.toEntity(planDto);
		Product product = productMapper.toEntity(planDto.getProduct());

		String id = subscriptionService.createPlan(plan, product);

		return new ResponseEntity<String>(id, HttpStatus.CREATED);
	}
}
