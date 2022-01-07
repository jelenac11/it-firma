package tim13.webshop.shop.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
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

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import tim13.webshop.shop.dto.ResponseUserDTO;
import tim13.webshop.shop.dto.UserLoginDTO;
import tim13.webshop.shop.dto.UserTokenStateDTO;
import tim13.webshop.shop.model.Role;
import tim13.webshop.shop.model.User;
import tim13.webshop.shop.repositories.IUserRepository;
import tim13.webshop.shop.security.TokenUtils;

@RestController
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowedHeaders = "*")
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private HttpServletRequest request;
	
    private final int MAX_ATTEMPT = 3;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	public AuthenticationController() {

	}

	private LoadingCache<String, Integer> loginFailsCache = CacheBuilder.newBuilder().
            expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
        public Integer load(String key) {
            return 0;
        }
    });
	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody UserLoginDTO authenticationRequest) {
		logger.trace("Login requested.");

		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		} catch (Exception e) {
			boolean blocked = loginFailed();
			if (blocked) {
				logger.info(String.format("3 or more unsuccessfull login attempts from ip address %s", getClientIP()));
				return new ResponseEntity<>("You tried to log in too many times. Your account wil be blocked for the next 24 hours.", HttpStatus.BAD_REQUEST);
			}
			System.out.println(e.getMessage());
			logger.trace(e.getMessage());
			return new ResponseEntity<>("Incorrect email or password.", HttpStatus.UNAUTHORIZED);
		}

		User user = (User) authentication.getPrincipal();
		if (!user.isEnabled()) {
			logger.info(String.format("Blocked user attempted to login from ip address %s", getClientIP()));
			return new ResponseEntity<>("You are not able to login because you are blocked.", HttpStatus.BAD_REQUEST);
		}
		List<Role> auth = user.getRoles();
		user.setLastSignInDate(LocalDateTime.now());
		userRepository.save(user);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenUtils.generateToken(user.getEmail(), auth.get(0).getName());
		int expiresIn = tokenUtils.getExpiredIn();

		logger.info(String.format("Successfull login from ip address %s", getClientIP()));
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
	
	public boolean loginFailed() {
        String key = getClientIP();
        int attempts;
        try {
            if (loginFailsCache.get(key) == MAX_ATTEMPT) {
               // logService.write(new Log(Log.INFO, Log.getServiceName(CLASS_PATH), CLASS_NAME, "CPW", String.format("Because of too many attempts, user from %s is blocked.", ipAddressProvider.get())));
            }
            attempts = loginFailsCache.get(key);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        loginFailsCache.put(key, attempts);
        return attempts >= MAX_ATTEMPT;
    }
	
	private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}
