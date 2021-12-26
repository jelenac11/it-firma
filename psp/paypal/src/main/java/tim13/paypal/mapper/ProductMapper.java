package tim13.paypal.mapper;

import org.springframework.stereotype.Component;

import tim13.paypal.dto.ProductDto;
import tim13.paypal.model.Product;

@Component
public class ProductMapper {

	public Product toEntity(ProductDto productDto) {
		Product product = new Product();

		product.setName(productDto.getName());
		product.setCategory(productDto.getCategory());
		product.setType(productDto.getType());

		return product;
	}

}
