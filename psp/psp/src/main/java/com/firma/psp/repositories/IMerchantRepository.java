package com.firma.psp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firma.psp.model.Merchant;

@Repository
public interface IMerchantRepository extends JpaRepository<Merchant, Long> {

	Merchant findByEmail(String email);

}
