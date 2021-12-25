package tim13.webshop.shop.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.model.Role;
import tim13.webshop.shop.repositories.IRoleRepository;

@Service
public class RoleService {

	@Autowired
	private IRoleRepository roleRepository;

	private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

	public List<Role> findById(Long id) {
		Optional<Role> role = this.roleRepository.findById(id);
		List<Role> roles = new ArrayList<>();
		if (role.isPresent()) {
			roles.add(role.get());
		}
		logger.info("Reading role from database by ID.");
		return roles;
	}

	public List<Role> findByName(String name) {
		Role role = this.roleRepository.findByName(name);
		List<Role> roles = new ArrayList<>();
		if (role != null) {
			roles.add(role);
		}
		logger.info("Reading role from database by name.");
		return roles;
	}

}
