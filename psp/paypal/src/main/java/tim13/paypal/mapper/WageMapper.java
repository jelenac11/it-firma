package tim13.paypal.mapper;

import org.springframework.stereotype.Component;

import tim13.paypal.dto.WageDTO;
import tim13.paypal.model.Wage;

@Component
public class WageMapper {

	public Wage toEntity(WageDTO wageDto) {
		Wage wage = new Wage();

		wage.setMerchantOrderId(wageDto.getMerchantOrderId());
		wage.setReceiver(wageDto.getReceiver());
		wage.setClientId(wageDto.getAttributes().stream()
				.filter(method -> method.getName().equals("Merchant client Id")).findFirst().get().getValue());
		wage.setClientSecret(wageDto.getAttributes().stream()
				.filter(method -> method.getName().equals("Merchant Client Secret")).findFirst().get().getValue());
		wage.setAmount(wageDto.getAmount());
		wage.setSuccessURL(wageDto.getSuccessURL());
		wage.setFailedURL(wageDto.getFailedURL());
		wage.setErrorURL(wageDto.getErrorURL());

		return wage;
	}

}
