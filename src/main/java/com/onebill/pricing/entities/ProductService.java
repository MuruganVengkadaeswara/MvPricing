package com.onebill.pricing.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "product_services")
public class ProductService {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ps_id")
	private int psId;

	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
	private Product product;

	@Column(name = "product_id")
	@NotNull
	private int productId;

	@ManyToOne
	@JoinColumn(name = "service_id", referencedColumnName = "service_id", insertable = false, updatable = false)
	private Service service;

	@Column(name = "service_id")
	@NotNull
	private int serviceId;

	@NotNull
	@Column(name = "service_price")
	private double servicePrice;

	@Column(name = "free_units")
	private long freeUnits;

}
