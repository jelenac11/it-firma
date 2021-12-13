package tim13.webshop.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tim13.webshop.shop.model.EquipmentShoppingCart;

@Repository
public interface IEquipmentShoppingCartRepository extends JpaRepository<EquipmentShoppingCart, Long> {

	@Query(value = "SELECT * FROM shopping_carts a WHERE a.user_id = ?1", nativeQuery = true)
	EquipmentShoppingCart findByUserId(Long userId);

}
