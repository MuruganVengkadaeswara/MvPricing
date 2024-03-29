package com.onebill.pricing.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", unique = true)
	private int productId;

	@NotNull
	@Column(name = "product_name", unique = true)
	@Size(max = 35)
	private String productName;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "product", orphanRemoval = true)
	private List<AdditionalPrice> additionalPrices;

	@OneToOne(mappedBy = "product", orphanRemoval = true)
	private ProductPrice price;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "product", orphanRemoval = true)
	private List<ProductService> services;

	@OneToMany(mappedBy = "product", orphanRemoval = true)
	private List<BundleProduct> bundleProducts;

}
