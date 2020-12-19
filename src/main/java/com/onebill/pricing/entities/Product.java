package com.onebill.pricing.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", unique = true)
	private int productId;

	@Column(name = "product_name")
	private String productName;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL)
	private List<ExtraPrice> extraPrices;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Plan> plans;

	@NotNull
	@OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
	private ProductPrice price;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductService> services;

}
