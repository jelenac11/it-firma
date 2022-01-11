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

import tim13.webshop.shop.dto.OrderDTO;
import tim13.webshop.shop.dto.PayWageDTO;
import tim13.webshop.shop.services.OrderService;

@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://localhost:8081", maxAge = 3600, allowedHeaders = "*")
public class OrderController {

	@Autowired
	private OrderService orderService;

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@PostMapping
	public ResponseEntity<?> addOrder(@RequestBody OrderDTO dto) {
		try {
			logger.trace("New order creation requested.");
			return new ResponseEntity<>(orderService.addOrder(dto), HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/wage")
	public ResponseEntity<?> addWage(@RequestBody PayWageDTO dto) {
		try {
			logger.trace("New order for wage creation requested.");
			return new ResponseEntity<>(orderService.addWage(dto), HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/transaction/{id}")
	public ResponseEntity<?> getOrderByTransaction(@PathVariable Long id) {
		logger.trace("Fetching order requested.");
		
		return new ResponseEntity<>(orderService.getOrderByTransaction(id).getTotalPrice(), HttpStatus.OK);
	}

}
