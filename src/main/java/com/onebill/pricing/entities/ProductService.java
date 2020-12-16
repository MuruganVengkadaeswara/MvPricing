package com.onebill.pricing.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class ProductService {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int psId;

	@OneToOne
	@NotNull
	private Product product;

	@OneToOne
	@NotNull
	private Service service;

	private double servicePrice;

	private long freeUnits;

}
