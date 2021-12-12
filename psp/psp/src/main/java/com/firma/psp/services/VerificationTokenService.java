package com.firma.psp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firma.psp.model.VerificationToken;
import com.firma.psp.repositories.IVerificationTokenRepository;

@Service
public class VerificationTokenService {

	@Autowired
	private IVerificationTokenRepository verificationTokenRepository;

	public VerificationToken findByToken(String token) {
		return verificationTokenRepository.findByToken(token);
	}

	public void saveToken(VerificationToken token) {
		verificationTokenRepository.save(token);
	}
}