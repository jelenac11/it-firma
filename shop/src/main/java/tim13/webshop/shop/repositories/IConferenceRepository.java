package tim13.webshop.shop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim13.webshop.shop.enums.ConferenceStatus;
import tim13.webshop.shop.model.Conference;

@Repository
public interface IConferenceRepository extends JpaRepository<Conference, Long> {

	List<Conference> findByStatusAndEndDateLessThan(ConferenceStatus status, Long date);

	List<Conference> findByStatusAndStartDateGreaterThan(ConferenceStatus status, Long date);

}
