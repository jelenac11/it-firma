package tim13.paypal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim13.paypal.model.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long> {

	Plan findOneByPlanId(String planId);
}
