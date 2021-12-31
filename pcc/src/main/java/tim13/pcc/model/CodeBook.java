package tim13.pcc.model;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "codebook")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CodeBook {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "identification_number", nullable = false, unique = true)
	private String identificationNumber;
	
	@Column(nullable = false)
	private String url;
}
