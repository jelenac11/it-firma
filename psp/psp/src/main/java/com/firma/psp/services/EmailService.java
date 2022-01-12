package com.firma.psp.services;

import java.util.UUID;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.firma.psp.model.Merchant;
import com.firma.psp.model.VerificationToken;

@EnableAsync
@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private VerificationTokenService verificationTokenService;

	@Async
	public void sendEmail(String to, String subject, String text) throws MailException, InterruptedException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setFrom(Environment.getProperties().getProperty("spring.mail.username"));
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);
	}

	@Async
	public void sendActivationLink(Merchant m) throws MailException, InterruptedException {
		String token = UUID.randomUUID().toString();
		VerificationToken vtoken = new VerificationToken();
		vtoken.setId(null);
		vtoken.setToken(token);
		vtoken.setMerchant(m);
		verificationTokenService.saveToken(vtoken);
//		String to = m.getEmail();
		String subject = "Sign Up Verification";
		String confirmationUrl = "/#/account-activation/" + token;
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo("jelenacupac99@gmail.com");
		email.setSubject(subject);
		email.setText(
				"For verification, please click on this link: " + "\r\n" + "https://localhost:8096" + confirmationUrl);
		javaMailSender.send(email);
	}

}
