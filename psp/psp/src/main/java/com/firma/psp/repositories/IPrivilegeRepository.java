package com.firma.psp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firma.psp.model.Privilege;

@Repository
public interface IPrivilegeRepository extends JpaRepository<Privilege, Long> {

	Privilege findByName(String name);

}
