package tim13.bank.bank.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim13.bank.bank.dto.MerchantDTO;
import tim13.bank.bank.model.Merchant;
import tim13.bank.bank.service.MerchantService;

@RestController
@RequestMapping(value = "/api/merchant", produces = MediaType.APPLICATION_JSON_VALUE)
public class MerchantController {

	@Autowired
	private MerchantService merchantService;
	
	private static final Logger logger = LoggerFactory.getLogger(MerchantController.class);
	
	@PostMapping()
	public ResponseEntity<?> createMerchant(@Valid @RequestBody MerchantDTO merchantDTO) {
		Merchant m = merchantService.save(merchantDTO);
		return ResponseEntity.ok(m);
	}
}
