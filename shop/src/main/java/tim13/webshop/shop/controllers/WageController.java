package tim13.webshop.shop.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim13.webshop.shop.services.WageService;

@RestController
@RequestMapping(value = "/api/wages", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://localhost:8081", maxAge = 3600, allowedHeaders = "*")
public class WageController {

	@Autowired
	WageService wageService;

	private static final Logger logger = LoggerFactory.getLogger(WageController.class);

	@GetMapping
	public ResponseEntity<?> getAll() {
		try {
			logger.trace("Requested fetching wages.");
			return new ResponseEntity<>(wageService.getByUser(), HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
