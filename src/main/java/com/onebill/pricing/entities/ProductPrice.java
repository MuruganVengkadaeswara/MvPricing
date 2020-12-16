package com.onebill.pricing.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name = "product_price")
public class ProductPrice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prod_price_id")
	private int prodPriceId;

	@NotNull
	@Column(name = "price")
	private double price;

	@OneToOne
	private Product products;

}
