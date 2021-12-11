package tim13.webshop.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tim13.webshop.shop.model.GeneralServiceShoppingCart;

@Repository
public interface IGeneralServiceShoppingCartRepository extends JpaRepository<GeneralServiceShoppingCart, Long> {

	@Query(value = "SELECT * FROM shopping_carts a WHERE a.user_id = ?1", nativeQuery = true)
	GeneralServiceShoppingCart findByUserId(Long userId);

}
