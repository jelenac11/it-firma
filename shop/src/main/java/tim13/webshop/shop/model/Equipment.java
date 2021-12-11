package tim13.webshop.shop.model;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "equipments")
public class Equipment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "equipment_id")
	private Long id;

	@Column
	private String name;

	@Column
	private String description;

	@Column
	private int quantity;

	@Column
	private int price;

	@Column(name = "equipment_type")
	@Enumerated(EnumType.STRING)
	private EquipmentType equipmentType;

	@OneToOne(mappedBy = "equipment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ChiefShoppingCartItem item;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "user_id")
	private Merchant merchant;

}
