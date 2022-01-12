package tim13.webshop.shop.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim13.webshop.shop.services.CourseService;

@RestController
@RequestMapping(value = "/api/courses", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseController {

	@Autowired
	private CourseService courseService;

	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

	@GetMapping
	public ResponseEntity<?> getAllCourses() {
		try {
			logger.trace("All courses requested.");
			return new ResponseEntity<>(courseService.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/by-merchant")
	public ResponseEntity<?> getAllCoursesByMerchant() {
		try {
			logger.trace("All courses requested.");

			return new ResponseEntity<>(courseService.findAllByMerchantId(), HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());

			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
