package com.onebill.pricing.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "extra_price")
public class ExtraPrice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "extra_id")
	private int extraId;

	private double price;
	private String description;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

}
