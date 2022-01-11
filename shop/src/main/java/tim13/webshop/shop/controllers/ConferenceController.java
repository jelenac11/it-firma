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

import tim13.webshop.shop.services.ConferenceService;

@RestController
@RequestMapping(value = "/api/conferences", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://localhost:8081", maxAge = 3600, allowedHeaders = "*")
public class ConferenceController {

	@Autowired
	private ConferenceService conferenceService;
	
	private static final Logger logger = LoggerFactory.getLogger(ConferenceController.class);

	@GetMapping
	public ResponseEntity<?> getAllConferences() {
		try {
			logger.trace("All conferences requested.");
			return new ResponseEntity<>(conferenceService.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
