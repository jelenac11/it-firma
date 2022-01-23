package tim13.webshop.shop.controllers;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim13.webshop.shop.dto.HDTO;
import tim13.webshop.shop.model.User;
import tim13.webshop.shop.repositories.IHistoryItemRepo;
import tim13.webshop.shop.services.EquipmentService;

@RestController
@RequestMapping(value = "/api/history", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://15d8-79-101-213-141.ngrok.io", maxAge = 3600, allowedHeaders = "*")
public class HistoryController {

	@Autowired
	private IHistoryItemRepo hRepo;

	private static final Logger logger = LoggerFactory.getLogger(HistoryController.class);

	@GetMapping
	public ResponseEntity<?> getHistory() {
		try {
			User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			logger.trace("History requested.");
			return new ResponseEntity<>(hRepo.findByBuyerId(current.getId()).stream().map(x -> new HDTO(x.getName(), x.getTotalPrice(), x.getQuantity())).collect(Collectors.toList()), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
