package tim13.webshop.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.repositories.IChiefShoppingCartRepository;

@Service
public class ChiefShoppingCartService {

	@Autowired
	private IChiefShoppingCartRepository chiefShoppingCartRepository;

}
