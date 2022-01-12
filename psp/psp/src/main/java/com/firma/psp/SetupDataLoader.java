package com.firma.psp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.firma.psp.model.Privilege;
import com.firma.psp.model.Role;
import com.firma.psp.repositories.IPrivilegeRepository;
import com.firma.psp.repositories.IRoleRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private IPrivilegeRepository privilegeRepository;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup)
			return;

		Privilege getSupportedMethods = createPrivilegeIfNotFound("GET_SUPPORTED_METHODS");

		Set<Privilege> merchantPrivileges = new HashSet<>(Arrays.asList(getSupportedMethods));
		createRoleIfNotFound("ROLE_MERCHANT", merchantPrivileges);

		alreadySetup = true;
	}

	@Transactional
	public Privilege createPrivilegeIfNotFound(String name) {
		Privilege privilege = privilegeRepository.findByName(name);
		if (privilege == null) {
			privilege = new Privilege();
			privilege.setName(name);
			return privilegeRepository.save(privilege);
		}
		return privilege;
	}

	@Transactional
	public void createRoleIfNotFound(String name, Set<Privilege> privileges) {
		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = new Role();
			role.setName(name);
		}
		role.setPrivileges(new ArrayList<>(privileges));
		roleRepository.save(role);
	}

}
