package tim13.webshop.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim13.webshop.shop.model.Privilege;

@Repository
public interface IPrivilegeRepository extends JpaRepository<Privilege, Long> {

	Privilege findByName(String name);

}
