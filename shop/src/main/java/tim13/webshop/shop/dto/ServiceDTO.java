package tim13.webshop.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim13.webshop.shop.model.Service;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {

	protected Long id;
	protected String name;
	protected double price;
	protected String merchant;
	protected boolean online;

	public ServiceDTO(Service s) {
		this.id = s.getId();
		this.name = s.getName();
		this.price = s.getPrice();
		this.merchant = s.getMerchant().getName();
		this.online = s.isOnline();
	}

}
