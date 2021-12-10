package tim13.webshop.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim13.webshop.shop.services.CourseService;

@RestController
@RequestMapping(value = "/api/courses", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowedHeaders = "*")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@GetMapping
	public ResponseEntity<?> getAllCourses() {
		try {
			return new ResponseEntity<>(courseService.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
