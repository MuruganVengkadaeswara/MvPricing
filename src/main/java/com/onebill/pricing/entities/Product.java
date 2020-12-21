package com.onebill.pricing.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
	private List<AdditionalPrice> additionalPrices;

	@OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE)
	private ProductPrice price;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
	private List<ProductService> services;

	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
	private List<BundleProduct> bundleProducts;

	@Column(name = "delete_status", columnDefinition = "boolean default false")
	@Getter
	private boolean deleteStatus;

}
