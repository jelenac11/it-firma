package tim13.paypal.common;

public class PaypalConstants {

	public static String MODE = "sandbox";
	public static String CURRENCY = "USD";
	public static String PAYMENT_METHOD = "paypal";
	public static String INTENT = "sale";
	public static String LINK_REL = "approval_url";
	public static String SUCCESS_URL = "http://localhost:8080/paypal/api/payment/execute/";
}
