package tim13.bitcoinservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tim13.bitcoinservice.dto.BitcoinOrderRequestDTO;
import tim13.bitcoinservice.dto.BitcoinOrderResponseDTO;
import tim13.bitcoinservice.dto.PaymentDataDTO;
import tim13.bitcoinservice.exceptions.InvalidDataException;

@Service
public class PaymentService {

	@Value("${CALLBACK_URL}")
    private String callback;
	
	@Value("${SANDBOX_URL}")
    private String sandbox;
	
	public String pay(PaymentDataDTO pd) {
		String token = pd.getAttributes().stream()
				.filter(attribute -> attribute.getName().equalsIgnoreCase("merchant token")).findFirst().get().getValue();
		
		BitcoinOrderRequestDTO bitcoinOrder = BitcoinOrderRequestDTO.builder()
                .order_id(pd.getMerchantOrderId().toString())
                .price_amount(pd.getAmount())
                .price_currency("USD")
                .receive_currency("BTC")
                .title("")
                .description("")
                .callback_url(callback)
                .cancel_url(pd.getFailedURL())
                .success_url(pd.getSuccessURL())
                .token(token)
                .purchaser_email("")
                .build();
		
		String clientSecret = "Bearer " + token;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", clientSecret);

        ResponseEntity<BitcoinOrderResponseDTO> response = null;
        
        try {
            response = new RestTemplate().exchange(sandbox, HttpMethod.POST, new HttpEntity<>(bitcoinOrder, headers), BitcoinOrderResponseDTO.class);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new InvalidDataException("Create order CoinGate failed", HttpStatus.BAD_REQUEST);
        }
        

        if (response.getBody() == null) {
            throw new InvalidDataException("Bitcoin service is not available", HttpStatus.BAD_GATEWAY);
        }
        
        String paymentUrl = response.getBody().getPayment_url();

        if (paymentUrl == null || paymentUrl.equals("")) {
            throw new InvalidDataException("Missing payment url from coingate", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return paymentUrl;
	}

}
