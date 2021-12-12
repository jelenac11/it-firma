package com.firma.psp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnTransformer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "payment_data")
public class PaymentData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_data_id")
	private Long id;

	@Column
	@ColumnTransformer(forColumn = "value", read = "pgp_sym_decrypt(value, current_setting('encrypt.key'), 'cipher-algo=aes256')", write = "pgp_sym_encrypt(?, current_setting('encrypt.key'), 'cipher-algo=aes256')")
	private String value;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "payment_method_attribute_id")
	private PaymentMethodAttribute attribute;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "merchant_id")
	private Merchant merchant;

}
