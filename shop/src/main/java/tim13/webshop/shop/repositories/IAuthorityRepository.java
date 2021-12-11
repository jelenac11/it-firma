package tim13.webshop.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim13.webshop.shop.model.Authority;

@Repository
public interface IAuthorityRepository extends JpaRepository<Authority, Long> {

	Authority findByName(String name);

}
