package tim13.paypal.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import tim13.paypal.converter.CryptoStringConverter;
import tim13.paypal.enums.TypeOfPlan;

@Getter
@Setter
@Entity
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String planId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = true)
	private String description;

	@Column(nullable = false)
	@Convert(converter = CryptoStringConverter.class)
	private String clientId;

	@Column(nullable = false)
	@Convert(converter = CryptoStringConverter.class)
	private String clientSecret;

	@Column(nullable = false)
	private Double amount;

	private TypeOfPlan typeOfPlan;

}
