package tim13.webshop.shop.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim13.webshop.shop.enums.ConferenceStatus;

@Entity
@DiscriminatorValue("CNF")
@Getter
@Setter
@NoArgsConstructor
public class Conference extends Service {

	@Enumerated(EnumType.STRING)
	private ConferenceStatus status;
}
