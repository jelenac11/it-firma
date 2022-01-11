package tim13.bankms.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tim13.bankms.dto.PaymentDataDTO;
import tim13.bankms.dto.PaymentRequestDTO;

@Service
public class PaymentService {

	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

	public String pay(PaymentDataDTO paymentDataDto) {
		logger.info("Paying order using bank");
		String id = paymentDataDto.getAttributes().stream()
				.filter(attribute -> attribute.getName().equalsIgnoreCase("merchant id")).findFirst().get().getValue();
		String password = paymentDataDto.getAttributes().stream()
				.filter(attribute -> attribute.getName().equalsIgnoreCase("merchant password")).findFirst().get()
				.getValue();

		PaymentRequestDTO pr = new PaymentRequestDTO(id, password, paymentDataDto.getMerchantOrderId(),
				paymentDataDto.getMerchantTimestamp(), paymentDataDto.getAmount(), paymentDataDto.getSuccessURL(),
				paymentDataDto.getFailedURL(), paymentDataDto.getErrorURL());

		RestTemplate rs = new RestTemplate();
		return rs.postForEntity("https://localhost:9002/api/payment/pay", pr, String.class).getBody();
	}

}
