package com.onebill.pricing.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "product_service")
public class ProductService {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ps_id")
	private int psId;

	@OneToOne
	@NotNull
	@JoinColumn(name = "product_id")
	private Product product;

	@OneToOne
	@NotNull
	@JoinColumn(name = "service_id")
	private Service service;

	@Column(name = "service_price")
	private double servicePrice;

	@Column(name = "free_units")
	private long freeUnits;

}
