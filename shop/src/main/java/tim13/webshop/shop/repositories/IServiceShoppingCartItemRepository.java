package tim13.webshop.shop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim13.webshop.shop.model.Service;
import tim13.webshop.shop.model.ServiceShoppingCartItem;

@Repository
public interface IServiceShoppingCartItemRepository extends JpaRepository<ServiceShoppingCartItem, Long> {

	List<ServiceShoppingCartItem> findByService(Service service);

}
