package com.onebill.pricing.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "bundles")
public class Bundle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bundle_id")
	private int bundleId;

	@Column(name = "bundle_name", unique = true)
	private String bundleName;

	@Column(name = "validity_days")
	private int validityDays;

	@OneToMany(mappedBy = "bundle", cascade = CascadeType.ALL)
	private List<BundleProduct> bundleProducts;

}
