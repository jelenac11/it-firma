package tim13.webshop.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.model.User;
import tim13.webshop.shop.repositories.IUserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
		} else {
			return user;
		}
	}

	public Iterable<User> getAll() {
		return null;
	}

	public User getById(Long id) {
		return null;
	}

	public User create(User entity) throws Exception {
		return null;
	}

	public boolean delete(Long id) throws Exception {
		return false;
	}

	public User update(Long id, User entity) throws Exception {
		return null;
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void save(User user) {
		userRepository.save(user);
	}

}
