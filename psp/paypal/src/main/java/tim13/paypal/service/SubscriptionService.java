package tim13.paypal.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.codec.binary.Base64;
import tim13.paypal.common.PaypalConstants;
import tim13.paypal.dto.CancellingSubscriptionDto;
import tim13.paypal.dto.SubscribeDto;
import tim13.paypal.model.Plan;
import tim13.paypal.model.Product;
import tim13.paypal.model.Subscription;
import tim13.paypal.repository.PlanRepository;
import tim13.paypal.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

	private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	public String createPlan(Plan plan, Product product) {
		String productId = createProduct(product, plan.getClientId(), plan.getClientSecret());

		JSONObject paymentPreference = createPaymentPreference();

		JSONObject frequency = createFrequency();

		JSONObject price = createPrice(plan.getAmount());

		JSONObject billingCycle = createBillingCycle(frequency, price);

		JSONArray billingCycles = new JSONArray();
		billingCycles.put(billingCycle);

		JSONObject planRequestData = new JSONObject();
		planRequestData.put("product_id", productId);
		planRequestData.put("name", plan.getName());
		planRequestData.put("description", plan.getDescription());
		planRequestData.put("status", PaypalConstants.PLAN_STATUS);
		planRequestData.put("payment_preferences", paymentPreference);
		planRequestData.put("billing_cycles", billingCycles);

		JSONObject responseJSON = sendRequestForCreatingPlan(PaypalConstants.CREATE_PLAN_URL, planRequestData,
				plan.getClientId(), plan.getClientSecret());

		String planId = responseJSON.getString("id");

		plan.setPlanId(planId);

		planRepository.save(plan);

		logger.info(String.format("Plan %s successfully created.", planId));

		return planId;
	}

	public String subscribe(SubscribeDto subscribeDto) {
		Plan plan = planRepository.findOneByPlanId(subscribeDto.getPlanId());

		JSONObject applicationContext = createApplicationContext(subscribeDto.getSuccessUrl(),
				subscribeDto.getCancelUrl());

		JSONObject subscriptionData = createSubscription(plan.getPlanId(), applicationContext);

		JSONObject responseJSON = sendRequestForSubscribingOnPlan(PaypalConstants.SUBSCRIBE_URL, subscriptionData,
				plan.getClientId(), plan.getClientSecret());

		logger.info(String.format("Subscription %s successfully created.", responseJSON.get("id")));

		Subscription subscription = new Subscription();
		subscription.setStartDate(subscriptionData.getString("start_time"));
		subscription.setSubscriptionId(responseJSON.get("id").toString());
		subscription.setPlanId(subscribeDto.getPlanId());

		subscriptionRepository.save(subscription);

		String redirectUrl = responseJSON.getJSONArray("links").getJSONObject(0).getString("href");

		return redirectUrl;
	}

	public void unsubscribe(String subscriptionId, CancellingSubscriptionDto dto) {
		Subscription subscription = subscriptionRepository.findBySubscriptionId(subscriptionId);
		Plan plan = planRepository.findOneByPlanId(subscription.getPlanId());

		JSONObject unsubscribe = new JSONObject();
		unsubscribe.put("reason", dto.getReason());

		sendRequestForCancellingSubscription(String.format(PaypalConstants.SUBSCRIPTION_CANCELLING_URL, subscriptionId),
				unsubscribe, plan.getClientId(), plan.getClientSecret());

		logger.info(String.format("Subscription %s cancelled.", subscriptionId));
	}

	private String createProduct(Product product, String clientId, String clientSecret) {
		JSONObject productData = new JSONObject();
		productData.put("name", product.getName());
		productData.put("type", product.getType());
		productData.put("category", product.getCategory());

		System.out.println(productData.toString());
		JSONObject responseJSON = sendRequestForCreatingProduct(PaypalConstants.CREATE_PRODUCT_URL, productData,
				clientId, clientSecret);

		String productId = responseJSON.getString("id");

		logger.info(String.format("Product %s successfully created.", productId));

		return productId;
	}

	private JSONObject createPaymentPreference() {
		JSONObject paymentPreference = new JSONObject();

		paymentPreference.put("auto_bill_outstanding", PaypalConstants.AUTO_BILL_OUTSTANDING);
		paymentPreference.put("setup_fee_failure_action", PaypalConstants.SETUP_FEE_FAILURE_ACTION);
		paymentPreference.put("payment_failure_threshold", PaypalConstants.PAYMENT_FAILURE_THRESHOLD);

		return paymentPreference;
	}

	private JSONObject createFrequency() {
		JSONObject frequency = new JSONObject();

		frequency.put("interval_unit", PaypalConstants.INTERVAL_UNIT);
		frequency.put("interval_count", PaypalConstants.INTERVAL_COUNT);

		return frequency;
	}

	private JSONObject createPrice(Double amount) {
		JSONObject price = new JSONObject();

		price.put("value", amount);
		price.put("currency_code", PaypalConstants.CURRENCY);

		return price;
	}

	private JSONObject createBillingCycle(JSONObject frequency, JSONObject price) {
		JSONObject billingCycle = new JSONObject();

		billingCycle.put("frequency", frequency);
		billingCycle.put("pricing_scheme", new JSONObject().put("fixed_price", price));
		billingCycle.put("tenure_type", PaypalConstants.TENURE_TYPE);
		billingCycle.put("sequence", PaypalConstants.SEQUENCE);
		billingCycle.put("total_cycles", PaypalConstants.TOTAL_CYCLES);

		return billingCycle;
	}

	private JSONObject createApplicationContext(String successUrl, String cancelUrl) {
		JSONObject applicationContext = new JSONObject();

		applicationContext.put("shipping_preference", PaypalConstants.SHIPPING_PREFERENCE);
		applicationContext.put("user_action", PaypalConstants.USER_ACTION);
		applicationContext.put("return_url", successUrl);
		applicationContext.put("cancel_url", cancelUrl);

		return applicationContext;
	}

	private JSONObject createSubscription(String planId, JSONObject applicationContext) {
		JSONObject subscription = new JSONObject();

		subscription.put("plan_id", planId);
		subscription.put("start_time", getTimeInFormatForPaypal());
		subscription.put("quantity", "1");
		subscription.put("application_context", applicationContext);

		return subscription;
	}

	private String getTimeInFormatForPaypal() {
		return LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT)
				.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "Z";
	}

	private JSONObject sendRequestForCreatingProduct(String url, JSONObject data, String merchantId,
			String merchantSecret) {
		JSONObject response = sendRequest(url, data, merchantId, merchantSecret);

		logger.info("Request for creating product sent");

		return response;
	}

	private JSONObject sendRequestForCreatingPlan(String url, JSONObject data, String merchantId,
			String merchantSecret) {
		JSONObject response = sendRequest(url, data, merchantId, merchantSecret);

		logger.info("Request for creating plan sent");

		return response;
	}

	private JSONObject sendRequestForSubscribingOnPlan(String url, JSONObject data, String merchantId,
			String merchantSecret) {
		JSONObject response = sendRequest(url, data, merchantId, merchantSecret);

		logger.info("Request for subscribing on plan sent");

		return response;
	}

	private JSONObject sendRequestForCancellingSubscription(String url, JSONObject data, String merchantId,
			String merchantSecret) {
		JSONObject response = sendRequest(url, data, merchantId, merchantSecret);

		logger.info("Request for cancelling subscription sent");

		return response;
	}

	private JSONObject sendRequest(String url, JSONObject data, String merchantId, String merchantSecret) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();

		headers.set("Authorization", createAuthorization(merchantId, merchantSecret));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<>(data.toString(), headers);

		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

		return response.getBody() != null ? new JSONObject(response.getBody()) : null;
	}

	private String createAuthorization(String merchantId, String merchantSecret) {
		String auth = merchantId + ":" + merchantSecret;

		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));

		return "Basic " + new String(encodedAuth);
	}
}
