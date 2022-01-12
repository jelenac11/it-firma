package tim13.webshop.shop.controllers;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import tim13.webshop.shop.dto.PlanDTO;
import tim13.webshop.shop.exceptions.BaseException;
import tim13.webshop.shop.services.PlanService;

@RestController
@RequestMapping(value = "/api/plans", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlanController {

	@Autowired
	private PlanService planService;

	private static final Logger logger = LoggerFactory.getLogger(PlanController.class);

	@GetMapping
	public ResponseEntity<?> getAllPlans() {
		logger.trace("All plans requested.");

		return new ResponseEntity<>(planService.findAll(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody PlanDTO planDTO) {
		try {
			logger.trace("Creating plan requested.");

			return new ResponseEntity<>(planService.create(planDTO), HttpStatus.CREATED);
		} catch (BaseException e) {
			return new ResponseEntity<>(e.getMessage(), e.getStatus());
		}
	}

}
