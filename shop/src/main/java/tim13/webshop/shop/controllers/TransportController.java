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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim13.webshop.shop.model.Conference;
import tim13.webshop.shop.repositories.IConferenceRepository;
import tim13.webshop.shop.repositories.ITransportRepository;

@RestController
@RequestMapping(value = "/api/transports", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://localhost:8081", maxAge = 3600, allowedHeaders = "*")
public class TransportController {

	@Autowired
	private ITransportRepository transportRepo;
	
	@Autowired
	private IConferenceRepository conferenceRepo;

	private static final Logger logger = LoggerFactory.getLogger(TransportController.class);

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getTransports(@PathVariable("id") Long id) {
		logger.trace("Get transports for conference with id " + id);
		Conference c = conferenceRepo.getOne(id);
		return new ResponseEntity<>(transportRepo.findByLocation(c.getLocation()), HttpStatus.OK);
	}

}
