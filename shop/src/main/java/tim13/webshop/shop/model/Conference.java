package tim13.webshop.shop.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("CNF")
@Getter
@Setter
@NoArgsConstructor
public class Conference extends Service {

}
