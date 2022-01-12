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
import tim13.webshop.shop.enums.WageStatus;

@Entity
@Getter
@Setter
public class Wage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wage_id")
	private Long id;

	@Column
	private String conference;

	@Column
	private Double duration;

	@Column
	private Long startDay;

	@Column
	private Long endDay;

	@Enumerated(EnumType.STRING)
	private WageStatus status;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "user_id")
	private User owner;
}
