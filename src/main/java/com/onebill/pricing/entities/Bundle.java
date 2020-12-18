package com.onebill.pricing.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "bundle")
public class Bundle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bundle_id")
	private int bundleId;

	private String extrasPaid;

	private int validityDays;

}
