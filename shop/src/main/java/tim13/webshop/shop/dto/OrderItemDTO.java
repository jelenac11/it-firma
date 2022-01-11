package tim13.webshop.shop.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

	@NotEmpty
	public Long itemId;
	@NotEmpty
	public Long productId;
	@NotEmpty
	public String itemType;

}
