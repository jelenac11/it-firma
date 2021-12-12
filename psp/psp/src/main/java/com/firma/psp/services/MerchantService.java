package com.firma.psp.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.firma.psp.dto.MerchantDTO;
import com.firma.psp.dto.MethodResponseDTO;
import com.firma.psp.dto.ShopDataDTO;
import com.firma.psp.exceptions.RequestException;
import com.firma.psp.model.Authority;
import com.firma.psp.model.Merchant;
import com.firma.psp.model.VerificationToken;
import com.firma.psp.repositories.IAuthorityRepository;
import com.firma.psp.repositories.IMerchantRepository;

@Service
public class MerchantService implements UserDetailsService {

	@Autowired
	private IMerchantRepository merchantRepository;

	@Autowired
	private IAuthorityRepository authorityRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private VerificationTokenService verificationTokenService;

	public boolean signUp(MerchantDTO merchantDTO) throws MailException, InterruptedException, RequestException {
		Merchant existing = merchantRepository.findByEmail(merchantDTO.getEmail());
		if (existing != null)
			throw new RequestException("User with this email already exists.");

		Merchant merchant = new Merchant();

		merchant.setEmail(merchantDTO.getEmail());
		merchant.setShopName(merchantDTO.getShopName());
		merchant.setPassword(encodePassword(merchantDTO.getPassword()));
		merchant.setWebSiteUrl(merchantDTO.getWebSiteUrl());
		merchant.setSuccessUrl(merchantDTO.getSuccessUrl());
		merchant.setFailedUrl(merchantDTO.getFailedUrl());
		merchant.setErrorUrl(merchantDTO.getErrorUrl());

		Set<Authority> auths = new HashSet<>();
		Authority a = authorityRepository.findByName("ROLE_MERCHANT");
		auths.add(a);

		merchant.setAuthorities(auths);
		merchant.setVerified(false);
		merchant.setSupportsPaymentMethods(false);
		merchant.setLastPasswordResetDate(System.currentTimeMillis());

		this.save(merchant);

		emailService.sendActivationLink(merchant);
		return true;
	}

	public void verify(String token) {
		VerificationToken vt = verificationTokenService.findByToken(token);

		if (vt != null) {
			vt.getMerchant().setVerified(true);
			save(vt.getMerchant());
		}
	}

	public List<MethodResponseDTO> getMerchantMethods(ShopDataDTO data) throws Exception {
		Merchant m = merchantRepository.findByEmail(data.getMerchant());
		if (m == null)
			throw new Exception("Merchant doesn't exist");
		return m.getPaymentMethods().stream().map(pm -> new MethodResponseDTO(pm.getId(), pm.getName()))
				.collect(Collectors.toList());
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Merchant user = merchantRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No merchant found with email '%s'.", email));
		} else {
			return user;
		}
	}

	public Iterable<Merchant> getAll() {
		return null;
	}

	public Merchant getById(Long id) {
		return null;
	}

	public Merchant create(Merchant entity) throws Exception {
		return null;
	}

	public boolean delete(Long id) throws Exception {
		return false;
	}

	public Merchant update(Long id, Merchant entity) throws Exception {
		return null;
	}

	public Merchant findByEmail(String email) {
		return merchantRepository.findByEmail(email);
	}

	public void save(Merchant user) {
		merchantRepository.save(user);
	}

	public String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

}
