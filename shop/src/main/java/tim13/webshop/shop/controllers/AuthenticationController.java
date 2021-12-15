package tim13.webshop.shop.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim13.webshop.shop.dto.ResponseUserDTO;
import tim13.webshop.shop.dto.UserLoginDTO;
import tim13.webshop.shop.dto.UserTokenStateDTO;
import tim13.webshop.shop.model.Role;
import tim13.webshop.shop.model.User;
import tim13.webshop.shop.security.TokenUtils;

@RestController
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowedHeaders = "*")
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	public AuthenticationController() {

	}

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

		User user = (User) authentication.getPrincipal();
		List<Role> auth = user.getRoles();

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenUtils.generateToken(user.getEmail(), auth.get(0).getName());
		int expiresIn = tokenUtils.getExpiredIn();

		logger.trace(String.format("User with ID: %s successfully logged in.", user.getId()));
		return ResponseEntity.ok(new UserTokenStateDTO(jwt, (long) expiresIn));
	}

	@GetMapping(value = "/current-user")
	public ResponseEntity<?> currentUser() {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return new ResponseEntity<>("Session expired.", HttpStatus.UNAUTHORIZED);
		}
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ResponseUserDTO responseUser = new ResponseUserDTO(current.getEmail(),
				((List<Role>) current.getRoles()).get(0).getName());
		return new ResponseEntity<>(responseUser, HttpStatus.OK);
	}

}
