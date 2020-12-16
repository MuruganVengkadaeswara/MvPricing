package com.onebill.pricing.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class ExtraPrice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int extraId;

	private double price;

	private String description;

	@ManyToOne
	private Product product;

}
