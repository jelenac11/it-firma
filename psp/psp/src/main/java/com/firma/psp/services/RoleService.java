package com.firma.psp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firma.psp.model.Role;
import com.firma.psp.repositories.IRoleRepository;

@Service
public class RoleService {

	@Autowired
	private IRoleRepository roleRepository;

	public List<Role> findById(Long id) {
		Optional<Role> auth = this.roleRepository.findById(id);
		List<Role> auths = new ArrayList<>();
		if (auth.isPresent()) {
			auths.add(auth.get());
		}
		return auths;
	}

	public List<Role> findByName(String name) {
		Role auth = this.roleRepository.findByName(name);
		List<Role> auths = new ArrayList<>();
		if (auth != null) {
			auths.add(auth);
		}
		return auths;
	}

}
