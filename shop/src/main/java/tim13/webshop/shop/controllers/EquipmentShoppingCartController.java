package tim13.webshop.shop.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim13.webshop.shop.dto.EquipmentShoppingCartItemDTO;
import tim13.webshop.shop.services.EquipmentShoppingCartService;

@RestController
@RequestMapping(value = "/api/equipment-shopping-carts", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://15d8-79-101-213-141.ngrok.io", maxAge = 3600, allowedHeaders = "*")
public class EquipmentShoppingCartController {

	@Autowired
	private EquipmentShoppingCartService equipmentShoppingCartService;

	private static final Logger logger = LoggerFactory.getLogger(EquipmentShoppingCartController.class);

	@GetMapping
	public ResponseEntity<?> getEquipmentShoppingCart() {
		try {
			logger.trace("Equipment shopping cart view requested.");
			return new ResponseEntity<>(equipmentShoppingCartService.getMyCart(), HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/add-item")
	public ResponseEntity<?> addItem(@RequestBody EquipmentShoppingCartItemDTO dto) {
		try {
			logger.trace("New equipment item addition requested.");
			return new ResponseEntity<>(equipmentShoppingCartService.addItem(dto), HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/remove-item/{id}")
	public ResponseEntity<?> removeItem(@PathVariable(value = "id") Long id) {
		try {
			logger.trace("Equipment item removal requested.");
			equipmentShoppingCartService.removeItem(id);
			return new ResponseEntity<>("Item successfully deleted", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
