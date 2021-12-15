package tim13.webshop.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim13.webshop.shop.model.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);

}
