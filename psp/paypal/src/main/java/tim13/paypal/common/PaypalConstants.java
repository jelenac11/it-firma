package tim13.paypal.common;

public class PaypalConstants {

	public static String MODE = "sandbox";
	public static String CURRENCY = "USD";
	public static String PAYMENT_METHOD = "paypal";
	public static String INTENT = "sale";
	public static String LINK_REL = "approval_url";
	public static String SUCCESS_URL = "https://localhost:8080/paypal/api/payment/execute/";
	public static String SUBSCRIPTION_CANCELLING_URL = "https://api.sandbox.paypal.com/v1/billing/subscriptions/%s/cancel";
	public static String SUBSCRIBE_URL = "https://api.sandbox.paypal.com/v1/billing/subscriptions";
	public static String CREATE_PLAN_URL = "https://api.sandbox.paypal.com/v1/billing/plans";
	public static String CREATE_PRODUCT_URL = "https://api.sandbox.paypal.com/v1/catalogs/products";
	public static String PAYING_WAGE = "https://api.sandbox.paypal.com/v1/payments/payouts";
	public static String AUTO_BILL_OUTSTANDING = "true";
	public static String SETUP_FEE_FAILURE_ACTION = "CONTINUE";
	public static String TENURE_TYPE = "REGULAR";
	public static String SHIPPING_PREFERENCE = "NO_SHIPPING";
	public static String USER_ACTION = "SUBSCRIBE_NOW";
	public static String PLAN_STATUS = "ACTIVE";
	public static String INTERVAL_UNIT = "MONTH";
	public static int SEQUENCE = 1;
	public static int TOTAL_CYCLES = 0;
	public static int PAYMENT_FAILURE_THRESHOLD = 1;
	public static int INTERVAL_COUNT = 1;

}
