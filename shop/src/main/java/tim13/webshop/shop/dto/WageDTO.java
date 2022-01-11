package tim13.webshop.shop.dto;

import lombok.Getter;
import lombok.Setter;
import tim13.webshop.shop.enums.WageStatus;

@Getter
@Setter
public class WageDTO {

	private Long id;

	private String conference;

	private Double duration;

	private Long startDay;

	private Long endDay;

	private WageStatus status;

}
