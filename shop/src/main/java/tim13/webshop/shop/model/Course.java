package tim13.webshop.shop.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("CRS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course extends Service {

	@Column
	private String teacher;

}
