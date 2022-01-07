package tim13.webshop.shop.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim13.webshop.shop.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	List<User> findByLastSignInDateLessThan(LocalDateTime minusDays);

}
