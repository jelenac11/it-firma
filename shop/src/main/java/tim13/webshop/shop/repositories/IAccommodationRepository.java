package tim13.webshop.shop.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim13.webshop.shop.model.Accommodation;

@Repository
public interface IAccommodationRepository extends JpaRepository<Accommodation, Long> {

	Set<Accommodation> findByLocation(String location);

}
