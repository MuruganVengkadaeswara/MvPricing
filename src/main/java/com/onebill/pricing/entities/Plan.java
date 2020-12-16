package com.onebill.pricing.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int planId;

	@OneToOne
	private Product product;

	private String extrasPaid;

	private int validityDays;

}
