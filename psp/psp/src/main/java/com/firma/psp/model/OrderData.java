package com.firma.psp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "order_data")
public class OrderData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_data_id")
	private Long id;

	@Column(name = "transaction_id")
	private Long transactionId;

	@Column(name = "merchant_email")
	private String merchantEmail;

	@Column(name = "total_price")
	private double totalPrice;

	@Column(name = "success_url")
	private String successUrl;

	@Column(name = "fail_url")
	private String failUrl;

	@Column(name = "error_url")
	private String errorUrl;

}
