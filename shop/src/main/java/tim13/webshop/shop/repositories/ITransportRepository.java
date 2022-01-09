package tim13.webshop.shop.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim13.webshop.shop.model.Transport;

@Repository
public interface ITransportRepository extends JpaRepository<Transport, Long> {

	Set<Transport> findByLocation(String location);

}
