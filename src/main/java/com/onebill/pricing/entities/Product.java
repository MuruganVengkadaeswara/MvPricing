package com.onebill.pricing.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;

	private String productName;

	@OneToMany(mappedBy = "product")
	private List<ExtraPrice> extraPrices;
	
	@OneToOne(mappedBy = "products")
	private ProductPrice price;
	
	
}
