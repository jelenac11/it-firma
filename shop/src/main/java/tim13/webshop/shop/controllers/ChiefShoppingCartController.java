package tim13.webshop.shop.controllers;

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

import tim13.webshop.shop.dto.ChiefShoppingCartItemDTO;
import tim13.webshop.shop.services.ChiefShoppingCartService;

@RestController
@RequestMapping(value = "/api/chief-shopping-carts", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowedHeaders = "*")
public class ChiefShoppingCartController {

	@Autowired
	private ChiefShoppingCartService chiefShoppingCartService;

	@GetMapping
	public ResponseEntity<?> getChiefShoppingCart() {
		try {
			return new ResponseEntity<>(chiefShoppingCartService.getMyCart(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/add-item")
	public ResponseEntity<?> addItem(@RequestBody ChiefShoppingCartItemDTO dto) {
		try {
			return new ResponseEntity<>(chiefShoppingCartService.addItem(dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/remove-item/{id}")
	public ResponseEntity<?> removeItem(@PathVariable(value = "id") Long id) {
		try {
			chiefShoppingCartService.removeItem(id);
			return new ResponseEntity<>("Item successfully deleted", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
