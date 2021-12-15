package tim13.webshop.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tim13.webshop.shop.model.Privilege;
import tim13.webshop.shop.model.Role;
import tim13.webshop.shop.repositories.IPrivilegeRepository;
import tim13.webshop.shop.repositories.IRoleRepository;

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

		Privilege buyService = createPrivilegeIfNotFound("BUY_SERVICE");
		Privilege buyEquipment = createPrivilegeIfNotFound("BUY_EQUIPMENT");
		Privilege readConferences = createPrivilegeIfNotFound("READ_CONFERENCES");
		Privilege readCourses = createPrivilegeIfNotFound("READ_COURSES");
		Privilege readEquipment = createPrivilegeIfNotFound("READ_EQUIPMENT");
		Privilege readESC = createPrivilegeIfNotFound("READ_ESC");
		Privilege addItemToESC = createPrivilegeIfNotFound("ADD_ITEM_TO_ESC");
		Privilege removeItemFromESC = createPrivilegeIfNotFound("REMOVE_ITEM_FROM_ESC");
		Privilege readSSC = createPrivilegeIfNotFound("READ_SSC");
		Privilege addItemToSSC = createPrivilegeIfNotFound("ADD_ITEM_TO_SSC");
		Privilege removeItemFromSSC = createPrivilegeIfNotFound("REMOVE_ITEM_FROM_SSC");
		Privilege addOrder = createPrivilegeIfNotFound("ADD_ORDER");

		Set<Privilege> merchantPrivileges = new HashSet<>(Arrays.asList(readConferences, readCourses, readEquipment));
		createRoleIfNotFound("ROLE_MERCHANT", merchantPrivileges);

		Set<Privilege> equipmentBuyerPrivileges = new HashSet<>(
				Arrays.asList(buyEquipment, readEquipment, readESC, addItemToESC, removeItemFromESC, addOrder));
		createRoleIfNotFound("ROLE_EQUIPMENT_BUYER", equipmentBuyerPrivileges);

		Set<Privilege> serviceBuyerPrivileges = new HashSet<>(Arrays.asList(buyService, readConferences, readCourses,
				readSSC, addItemToSSC, removeItemFromSSC, addOrder));
		createRoleIfNotFound("ROLE_SERVICE_BUYER", serviceBuyerPrivileges);

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
