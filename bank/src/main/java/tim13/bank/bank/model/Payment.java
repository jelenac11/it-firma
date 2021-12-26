package tim13.bank.bank.model;

import java.sql.Timestamp;

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
public class Payment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long merchantOrderId;

    @Column(nullable = false)
    private Timestamp merchantTimestamp;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    @Convert(converter = CryptoStringConverter.class)
    private String merchantId;

    @Column(nullable = false)
    private String successUrl;

    @Column(nullable = false)
    private String failedUrl;

    @Column(nullable = false)
    private String errorUrl;

}
