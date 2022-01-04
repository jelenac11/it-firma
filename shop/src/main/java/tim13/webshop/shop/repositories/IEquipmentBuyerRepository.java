package tim13.webshop.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim13.webshop.shop.model.EquipmentBuyer;

@Repository
public interface IEquipmentBuyerRepository extends JpaRepository<EquipmentBuyer, Long> {

}
