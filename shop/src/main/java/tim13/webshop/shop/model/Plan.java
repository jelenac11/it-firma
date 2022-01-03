package tim13.webshop.shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import tim13.webshop.shop.enums.TypeOfPlan;

@Entity
@Getter
@Setter
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plan_id")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = true)
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "service_id")
	private Course course;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TypeOfPlan typeOfPlan;

	@Column(nullable = false)
	private Double price;

	@Column(nullable = true)
	private String paypalPlanId;
}
