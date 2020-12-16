package com.onebill.pricing.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "plan")
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plan_id")
	private int planId;

	@OneToOne
	private Product product;

	@Column(name = "extras_paid")
	private String extrasPaid;

	@Column(name = "validity_days")
	private int validityDays;

}
