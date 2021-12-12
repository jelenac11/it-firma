package com.firma.psp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firma.psp.dto.ShopDataDTO;
import com.firma.psp.services.MerchantService;

@RestController
@RequestMapping(value = "/api/merchants", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8096", maxAge = 3600, allowedHeaders = "*")
public class MerchantController {
	
	@Autowired
	private MerchantService merchantService;
	
	@PostMapping(value = "supported-methods")
	public ResponseEntity<?> getMerchantMethods(@Valid @RequestBody ShopDataDTO data) {
		try {
			return new ResponseEntity<>(merchantService.getMerchantMethods(data), HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); 
		}
	}
}
