package tim13.webshop.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tim13.webshop.shop.model.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

	@Query(value = "select * from orders o where o.order_id = (select t.id from transaction t where id = ?1)", nativeQuery = true)
	Order getByTransactionId(Long id);
}
