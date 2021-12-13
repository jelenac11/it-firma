package com.firma.psp.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.firma.psp.dto.AttributeDTO;
import com.firma.psp.dto.AttributeValueResponseDTO;
import com.firma.psp.dto.ChosenPaymentMethodDTO;
import com.firma.psp.dto.ChosenPaymentMethodsDTO;
import com.firma.psp.dto.NewAttributeDTO;
import com.firma.psp.dto.NewPaymentMethodDTO;
import com.firma.psp.dto.PaymentAttributeValueDTO;
import com.firma.psp.dto.PaymentMethodDTO;
import com.firma.psp.dto.PaymentRequestDTO;
import com.firma.psp.dto.PaypalDataDTO;
import com.firma.psp.dto.SupportedMethodResponseDTO;
import com.firma.psp.model.Merchant;
import com.firma.psp.model.PaymentData;
import com.firma.psp.model.PaymentMethod;
import com.firma.psp.model.PaymentMethodAttribute;
import com.firma.psp.repositories.IPaymentDataRepository;
import com.firma.psp.repositories.IPaymentMethodAttributeRepository;
import com.firma.psp.repositories.IPaymentMethodRepository;

@Service
public class PaymentMethodService {

	@Autowired
	private IPaymentMethodRepository methodRepo;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private IPaymentMethodAttributeRepository attributeRepo;

	@Autowired
	private IPaymentDataRepository dataRepo;

	public List<PaymentMethodDTO> getAll() {
		List<PaymentMethod> allMethods = methodRepo.findAll();
		List<PaymentMethodDTO> result = new ArrayList<PaymentMethodDTO>();
		for (PaymentMethod pm : allMethods) {
			List<AttributeDTO> attributes = pm.getAttributes().stream()
					.map(a -> new AttributeDTO(a.getId(), a.getName(), a.getType())).collect(Collectors.toList());
			result.add(new PaymentMethodDTO(pm.getId(), pm.getName(), attributes));

		}
		return result;
	}

	public void addSupportedMethods(ChosenPaymentMethodsDTO methods) throws Exception {
		Merchant current = (Merchant) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Set<PaymentMethod> m = new HashSet<PaymentMethod>();
		for (ChosenPaymentMethodDTO cpm : methods.getChosenMethods()) {
			PaymentMethod pm = methodRepo.findById(cpm.getId()).orElse(null);
			if (pm == null)
				throw new Exception("Payment method doesn't exist");
			m.add(pm);
			checkAttributes(pm, cpm);
		}
		current.setPaymentMethods(m);
		current.setSupportsPaymentMethods(true);
		merchantService.save(current);
		for (ChosenPaymentMethodDTO cpm : methods.getChosenMethods()) {
			for (PaymentAttributeValueDTO a : cpm.getValues()) {
				PaymentMethodAttribute attribute = attributeRepo.getOne(a.getId());
				PaymentData pd = dataRepo.findByMerchantIdAndAttributeId(current.getId(), a.getId());
				if (pd == null) {
					pd = new PaymentData();
					pd.setValue(a.getValue());
					pd.setMerchant(current);
					pd.setAttribute(attribute);
				} else {
					pd.setValue(a.getValue());
				}
				dataRepo.save(pd);
			}
		}
	}

	public List<SupportedMethodResponseDTO> getMethods() {
		List<PaymentMethod> pms = methodRepo.findAll();
		Merchant current = (Merchant) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<PaymentMethod> supported = new ArrayList<>(current.getPaymentMethods());
		List<SupportedMethodResponseDTO> result = new ArrayList<SupportedMethodResponseDTO>();
		List<PaymentMethod> notSupported = new ArrayList<PaymentMethod>();
		for (PaymentMethod pm : pms) {
			boolean exists = false;
			for (PaymentMethod m : supported) {
				if (m.getId() == pm.getId())
					exists = true;
			}
			if (!exists)
				notSupported.add(pm);
		}
		for (PaymentMethod pm : supported) {
			List<AttributeValueResponseDTO> values = new ArrayList<AttributeValueResponseDTO>();
			List<PaymentMethodAttribute> attributes = new ArrayList<>(pm.getAttributes());
			for (PaymentMethodAttribute a : attributes) {
				PaymentData pd = dataRepo.findByMerchantIdAndAttributeId(current.getId(), a.getId());
				values.add(new AttributeValueResponseDTO(a.getId(), pd.getAttribute().getName(), pd.getValue(),
						pd.getAttribute().getType()));
			}
			result.add(new SupportedMethodResponseDTO(pm.getId(), pm.getName(), true, values));
		}
		for (PaymentMethod pm : notSupported) {
			List<PaymentMethodAttribute> attributes = new ArrayList<>(pm.getAttributes());
			List<AttributeValueResponseDTO> values = attributes.stream()
					.map(p -> new AttributeValueResponseDTO(p.getId(), p.getName(), "", p.getType()))
					.collect(Collectors.toList());
			result.add(new SupportedMethodResponseDTO(pm.getId(), pm.getName(), false, values));
		}
		return result;
	}

	public void save(NewPaymentMethodDTO newMethod) {
		List<PaymentMethodAttribute> attrs = new ArrayList<PaymentMethodAttribute>();
		for (NewAttributeDTO a : newMethod.getAttributes()) {
			PaymentMethodAttribute attr = new PaymentMethodAttribute();
			attr.setName(a.getName());
			attr.setType(a.getType());
		}
		PaymentMethod pm = new PaymentMethod();
		pm.setName(newMethod.getName());
		pm.setUri(newMethod.getUri());
		pm.setAttributes(new HashSet<>(attrs));
		methodRepo.save(pm);
	}

	private void checkAttributes(PaymentMethod pm, ChosenPaymentMethodDTO cpm) throws Exception {
		Set<PaymentMethodAttribute> attributes = pm.getAttributes();
		for (PaymentMethodAttribute a : attributes) {
			boolean exists = false;
			for (PaymentAttributeValueDTO v : cpm.getValues()) {
				if (v.getId() == a.getId())
					exists = true;
			}
			if (!exists)
				throw new Exception("Payment method attribute incorrect!");
		}

	}

	public String getPaymentUrl(PaymentRequestDTO paymentRequest) {
		Optional<PaymentMethod> paymentMethod = methodRepo.findById(paymentRequest.getPaymentMethodId());
		Merchant merchant = merchantService.findByEmail(paymentRequest.getMerchantEmail());

		if (paymentMethod.isEmpty() || merchant == null) {
			return null;
		}

		Set<PaymentMethodAttribute> paymentMethodAttributes = paymentMethod.get().getAttributes();

		switch (paymentMethod.get().getId().toString()) {
		case "1":

			break;
		case "2":
			return getUrlByPaypal(merchant, paymentMethodAttributes, paymentRequest, paymentMethod.get());
		default:
			break;
		}

		return null;
	}

	private String getUrlByPaypal(Merchant merchant, Set<PaymentMethodAttribute> paymentAttributes,
			PaymentRequestDTO paymentRequestDTO, PaymentMethod paymentMethod) {
		PaypalDataDTO paypalDataDTO = createPaypalData(merchant, paymentAttributes, paymentRequestDTO);

		return sendRequestToPaypal(paymentMethod, paypalDataDTO);
	}

	private PaypalDataDTO createPaypalData(Merchant merchant, Set<PaymentMethodAttribute> paymentAttributes,
			PaymentRequestDTO paymentRequestDTO) {
		PaypalDataDTO paypalDataDTO = new PaypalDataDTO();

		paypalDataDTO.setAmount(paymentRequestDTO.getAmount());
		paypalDataDTO.setCancelUrl(merchant.getFailedUrl());
		paypalDataDTO.setClientId(getMerchantClientId(paymentAttributes, merchant));
		paypalDataDTO.setClientSecret(getMerchantSecret(paymentAttributes, merchant));
		paypalDataDTO.setErrorUrl(merchant.getErrorUrl());
		paypalDataDTO.setMerchantOrderId(paymentRequestDTO.getMerchantOrderId());
		paypalDataDTO.setSuccessUrl(merchant.getSuccessUrl());

		return paypalDataDTO;
	}

	private String getMerchantClientId(Set<PaymentMethodAttribute> paymentAttributes, Merchant merchant) {
		return paymentAttributes.stream()
				.filter(attribute -> attribute.getName().equalsIgnoreCase("merchant client id")).findFirst().get()
				.getData().stream().filter(data -> data.getMerchant().getId() == merchant.getId()).findFirst().get()
				.getValue();
	}

	private String getMerchantSecret(Set<PaymentMethodAttribute> paymentAttributes, Merchant merchant) {
		return paymentAttributes.stream()
				.filter(attribute -> attribute.getName().equalsIgnoreCase("merchant client secret")).findFirst().get()
				.getData().stream().filter(data -> data.getMerchant().getId() == merchant.getId()).findFirst().get()
				.getValue();
	}

	private String sendRequestToPaypal(PaymentMethod paymentMethod, PaypalDataDTO paypalDataDTO) {
		RestTemplate rs = new RestTemplate();

		String response = rs.postForEntity(paymentMethod.getUri(), paypalDataDTO, String.class).getBody();

		return response;
	}
}
