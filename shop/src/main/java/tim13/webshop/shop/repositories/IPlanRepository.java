package tim13.webshop.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tim13.webshop.shop.model.Plan;

public interface IPlanRepository extends JpaRepository<Plan, Long> {

}
