package com.firma.psp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firma.psp.model.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);

}
