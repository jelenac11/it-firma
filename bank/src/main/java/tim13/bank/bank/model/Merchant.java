package tim13.bank.bank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnTransformer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @ColumnTransformer(forColumn = "merchant_id", read = "pgp_sym_decrypt(merchant_id, current_setting('encrypt.key'), 'cipher-algo=aes256')", write = "pgp_sym_encrypt(?, current_setting('encrypt.key'), 'cipher-algo=aes256')")
    private String merchantId;

    @Column(nullable = false)
    @ColumnTransformer(forColumn = "merchant_password", read = "pgp_sym_decrypt(merchant_password, current_setting('encrypt.key'), 'cipher-algo=aes256')", write = "pgp_sym_encrypt(?, current_setting('encrypt.key'), 'cipher-algo=aes256')")
    private String merchantPassword;
}
