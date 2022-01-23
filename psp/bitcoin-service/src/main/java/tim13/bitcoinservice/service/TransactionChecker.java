package tim13.bitcoinservice.service;

import lombok.SneakyThrows;
import tim13.bitcoinservice.dto.BitcoinOrderResponseDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TransactionChecker {

	@Autowired
	private PaymentService paymentService;

	private static final Logger logger = LoggerFactory.getLogger(TransactionChecker.class);

	@SneakyThrows
	@Async
	public void checkTransaction(Long id, String coingateApiKey) {
		logger.info("Checking transaction with id " + id);
		String getOrderSandboxUrl = "https://api-sandbox.coingate.com/v2/orders/" + id;

		String clientSecret = "Bearer " + coingateApiKey;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", clientSecret);

		ResponseEntity<BitcoinOrderResponseDTO> responseEntity;

		do {
			responseEntity = new RestTemplate().exchange(getOrderSandboxUrl, HttpMethod.GET, new HttpEntity<>(headers),
					BitcoinOrderResponseDTO.class);
			Thread.sleep(5000);

		} while (responseEntity.getBody().getStatus().equals("new")
				|| responseEntity.getBody().getStatus().equals("pending")
				|| responseEntity.getBody().getStatus().equals("confirming"));

		paymentService.completePayment(responseEntity.getBody());
	}

}