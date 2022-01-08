package tim13.bank.bank.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim13.bank.bank.converter.CryptoStringConverter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Merchant {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false, unique = true)
    @Convert(converter = CryptoStringConverter.class)
    private String merchantId;

    @Column(nullable = false)
    @Convert(converter = CryptoStringConverter.class)
    private String merchantPassword;
}
