package com.onebill.pricing.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
@Table(name = "additional_prices")
public class AdditionalPrice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "addl_price_id")
	private int addlPriceId;

	@NotNull
	@Column(name = "price", columnDefinition = "Double(10,2)")
	private double price;

	@NotNull
	@Size(max = 50)
	private String description;

	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
	private Product product;

	@Column(name = "product_id")
	private int productId;

}
