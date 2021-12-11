package tim13.webshop.shop.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.model.Authority;
import tim13.webshop.shop.repositories.IAuthorityRepository;

@Service
public class AuthorityService {

	@Autowired
	private IAuthorityRepository authorityRepository;

	public List<Authority> findById(Long id) {
		Optional<Authority> auth = this.authorityRepository.findById(id);
		List<Authority> auths = new ArrayList<>();
		if (auth.isPresent()) {
			auths.add(auth.get());
		}
		return auths;
	}

	public List<Authority> findByName(String name) {
		Authority auth = this.authorityRepository.findByName(name);
		List<Authority> auths = new ArrayList<>();
		if (auth != null) {
			auths.add(auth);
		}
		return auths;
	}

}
