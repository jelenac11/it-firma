package tim13.webshop.shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim13.webshop.shop.enums.TransportType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "transports")
public class Transport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transport_id")
	private Long id;

	@Column(name = "price")
	private double price;

	@Column(name = "location")
	private String location;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private TransportType type;

}
