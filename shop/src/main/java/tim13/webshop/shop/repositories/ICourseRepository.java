package tim13.webshop.shop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tim13.webshop.shop.model.Course;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Long> {

	@Query(value = "select * from services s where s.type = 'CRS' and s.user_id = ?1", nativeQuery = true)
	List<Course> findByMerchantId(Long merchantId);

}
