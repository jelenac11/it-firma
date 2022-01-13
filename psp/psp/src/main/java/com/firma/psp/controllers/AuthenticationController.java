package com.firma.psp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firma.psp.dto.MerchantDTO;
import com.firma.psp.dto.ResponseUserDTO;
import com.firma.psp.dto.UserLoginDTO;
import com.firma.psp.dto.UserTokenStateDTO;
import com.firma.psp.exceptions.RequestException;
import com.firma.psp.model.Role;
import com.firma.psp.model.Merchant;
import com.firma.psp.security.TokenUtils;
import com.firma.psp.services.MerchantService;

@RestController
@CrossOrigin(origins = "https://localhost:8096", maxAge = 3600, allowedHeaders = "*")
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MerchantService merchantService;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody UserLoginDTO authenticationRequest) {
		logger.trace("Login requested.");
		
		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>("Incorrect email or password.", HttpStatus.UNAUTHORIZED);
		}

		Merchant user = (Merchant) authentication.getPrincipal();

		if (!user.isVerified())
			return new ResponseEntity<>("Account not verified.", HttpStatus.BAD_REQUEST);

		@SuppressWarnings("unchecked")
		List<Role> roles = (List<Role>) user.getAuthorities();

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String role = "";
		for (Role a : roles) {
			role = a.getName();
		}

		String jwt = tokenUtils.generateToken(user.getEmail(), role);
		int expiresIn = tokenUtils.getExpiredIn();

		logger.trace(String.format("User with ID: %s successfully logged in.", user.getId()));
		if (!user.isSupportsPaymentMethods()) {
			return ResponseEntity.ok(new UserTokenStateDTO(jwt, false, (long) expiresIn));
		}
		return ResponseEntity.ok(new UserTokenStateDTO(jwt, true, (long) expiresIn));
	}

	@PostMapping(value = "/sign-up")
	public ResponseEntity<?> signUp(@RequestBody MerchantDTO merchantDTO) {
		try {
			logger.trace("Sign up requested.");
			return new ResponseEntity<>(merchantService.signUp(merchantDTO), HttpStatus.OK);
		} catch (RequestException e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (InterruptedException | MailException e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/verify/{token}")
	public ResponseEntity<?> verify(@PathVariable("token") String url) {
		merchantService.verify(url);
		return new ResponseEntity<>("Verified.", HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/current-user")
	public ResponseEntity<?> currentUser() {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return new ResponseEntity<>("Session expired.", HttpStatus.UNAUTHORIZED);
		}
		Merchant current = (Merchant) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String role = "";
		for (Role a : (List<Role>) current.getAuthorities()) {
			role = a.getName();
		}

		ResponseUserDTO responseUser = new ResponseUserDTO(current.getEmail(), role, current.isSupportsPaymentMethods());
		return new ResponseEntity<>(responseUser, HttpStatus.OK);
	}

}
