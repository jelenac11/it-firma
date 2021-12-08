package tim13.paypal.util;

public interface ICurrencyConverter {

	Double convert(String from, String to, Double amount);
}
