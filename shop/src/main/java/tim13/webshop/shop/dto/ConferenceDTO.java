package tim13.webshop.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim13.webshop.shop.model.Conference;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceDTO extends ServiceDTO {

	private String description;
	private String location;
	private Long startDate;
	private Long endDate;

	public ConferenceDTO(Conference c) {
		this.id = c.getId();
		this.name = c.getName();
		this.description = c.getDescription();
		this.location = c.getLocation();
		this.price = c.getPrice();
		this.startDate = c.getStartDate();
		this.endDate = c.getEndDate();
		this.merchant = c.getMerchant().getName();
	}

}
