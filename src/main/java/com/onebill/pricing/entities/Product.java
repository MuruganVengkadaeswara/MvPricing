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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", unique = true)
	private int productId;

	@Column(name = "product_name", unique = true)
	private String productName;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL)
	private List<AdditionalPrice> additionalPrices;

	@OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
	private ProductPrice price;

	@OneToMany( mappedBy = "product", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ProductService> services;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<BundleProduct> bundlesProducts;

}
