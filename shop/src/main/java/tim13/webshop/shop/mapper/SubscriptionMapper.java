package tim13.webshop.shop.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tim13.webshop.shop.dto.SubscriptionDTO;
import tim13.webshop.shop.model.Subscription;

@Component
public class SubscriptionMapper {

	@Autowired
	private PlanMapper planMapper;

	public SubscriptionDTO toDTO(Subscription subscription) {
		SubscriptionDTO dto = new SubscriptionDTO();

		dto.setId(subscription.getId());
		dto.setStartDate(subscription.getStartDate());
		dto.setPlan(planMapper.toDTO(subscription.getPlan()));

		return dto;
	}
}
