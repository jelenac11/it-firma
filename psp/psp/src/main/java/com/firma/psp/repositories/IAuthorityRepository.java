package com.firma.psp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firma.psp.model.Authority;

@Repository
public interface IAuthorityRepository extends JpaRepository<Authority, Long> {

	Authority findByName(String name);

}
