package tim13.webshop.shop.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim13.webshop.shop.model.Accommodation;
import tim13.webshop.shop.model.HistoryItem;

@Repository
public interface IHistoryItemRepo extends JpaRepository<HistoryItem, Long> {

	List<HistoryItem> findByBuyer(Long id);

	List<HistoryItem> findByBuyerId(Long id);

}
