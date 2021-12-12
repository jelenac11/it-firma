package com.firma.psp.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "payment_method_attributes")
public class PaymentMethodAttribute {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_method_attribute_id")
	private Long id;

	@Column
	private String name;

	@Column
	private String type;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "payment_method_id")
	private PaymentMethod method;

	@OneToMany(mappedBy = "attribute")
	private Set<PaymentData> data;

}
