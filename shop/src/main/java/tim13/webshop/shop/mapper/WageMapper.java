package tim13.webshop.shop.mapper;

import org.springframework.stereotype.Component;

import tim13.webshop.shop.dto.WageDTO;
import tim13.webshop.shop.model.Wage;

@Component
public class WageMapper {

	public WageDTO toDTO(Wage wage) {
		WageDTO dto = new WageDTO();

		dto.setConference(wage.getConference());
		dto.setDuration(wage.getDuration());
		dto.setEndDay(wage.getEndDay());
		dto.setId(wage.getId());
		dto.setStartDay(wage.getStartDay());
		dto.setStatus(wage.getStatus());

		return dto;
	}
}
