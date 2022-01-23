package tim13.bitcoinservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.SneakyThrows;
import tim13.bitcoinservice.dto.BitcoinOrderRequestDTO;
import tim13.bitcoinservice.dto.BitcoinOrderResponseDTO;
import tim13.bitcoinservice.dto.PaymentDataDTO;
import tim13.bitcoinservice.enums.TransactionStatus;
import tim13.bitcoinservice.exceptions.InvalidDataException;

@Service
public class PaymentService {

	@Value("${CALLBACK_URL}")
	private String callback;

	@Value("${SANDBOX_URL}")
	private String sandbox;
	
	@Autowired
	@Lazy
	private TransactionChecker transactionChecker;
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

	public String pay(PaymentDataDTO pd) {
		logger.info("Payment requested for payment with id " + pd.getMerchantOrderId());
		String token = pd.getAttributes().stream()
				.filter(attribute -> attribute.getName().equalsIgnoreCase("merchant token")).findFirst().get()
				.getValue();

		BitcoinOrderRequestDTO bitcoinOrder = BitcoinOrderRequestDTO.builder()
				.order_id(pd.getMerchantOrderId().toString()).price_amount(pd.getAmount()).price_currency("USD")
				.receive_currency("BTC").title("").description("").callback_url(callback)
				.cancel_url(pd.getFailedURL() + "/" + pd.getMerchantOrderId())
				.success_url(pd.getSuccessURL() + "/" + pd.getMerchantOrderId()).token(token).purchaser_email("")
				.build();

		String clientSecret = "Bearer " + token;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", clientSecret);

		ResponseEntity<BitcoinOrderResponseDTO> response = null;

		try {
			response = new RestTemplate().exchange(sandbox, HttpMethod.POST, new HttpEntity<>(bitcoinOrder, headers),
					BitcoinOrderResponseDTO.class);
		} catch (Exception e) {
			logger.info("Create order CoinGate failed for payment with id " + pd.getMerchantOrderId());
			throw new InvalidDataException("Create order CoinGate failed", HttpStatus.BAD_REQUEST);
		}

		if (response.getBody() == null) {
			throw new InvalidDataException("Bitcoin service is not available", HttpStatus.BAD_GATEWAY);
		}

		String paymentUrl = response.getBody().getPayment_url();

		if (paymentUrl == null || paymentUrl.equals("")) {
			throw new InvalidDataException("Missing payment url from coingate", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		new Thread(new Runnable() {
	        private ResponseEntity<BitcoinOrderResponseDTO> param;

	        public Runnable init(ResponseEntity<BitcoinOrderResponseDTO> param) {
	            this.param = param;
	            return this;
	        }

	        @Override
	        public void run() {
	        	transactionChecker.checkTransaction(this.param.getBody().getId(), token);
	        }
	    }.init(response)).start();

		return paymentUrl;
	}

	public void completePayment(BitcoinOrderResponseDTO data) {
		logger.info("Completing payment");
		Long transactionId;
		try {
			transactionId = Long.parseLong(data.getOrder_id());
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Transaction id has wrong format");
		}

		if (data.getStatus().equals("paid")) {
			sendTransactionResponse(transactionId, TransactionStatus.SUCCESS);
		} else if (data.getStatus().equals("expired") || data.getStatus().equals("canceled")) {
			sendTransactionResponse(transactionId, TransactionStatus.FAILED);
		} else {
			sendTransactionResponse(transactionId, TransactionStatus.ERROR);
		}
	}

	@Async
	@SneakyThrows
	public void sendTransactionResponse(Long transactionId, TransactionStatus status) {
		int statusInt = 0;
		if (status.equals(TransactionStatus.SUCCESS))
			statusInt = 1;
		if (status.equals(TransactionStatus.FAILED))
			statusInt = 2;
		if (status.equals(TransactionStatus.ERROR))
			statusInt = 3;
		HttpHeaders headers = new HttpHeaders();
		new RestTemplate().exchange("https://localhost:8089/api/transaction/" + transactionId + "?status=" + statusInt,
				HttpMethod.PUT, new HttpEntity<>(headers), Object.class);
	}

}
