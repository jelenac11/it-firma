package tim13.paypal.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

@Component
public class CurrencyConverter implements ICurrencyConverter {

	private Double rate = 0.0096;

	@Override
	public Double convert(String from, String to, Double amount) {
		Double convertedAmount = amount * rate;

		BigDecimal roundedValue = new BigDecimal(convertedAmount);

		roundedValue = roundedValue.setScale(2, RoundingMode.HALF_UP);

		return roundedValue.doubleValue();
	}

}
