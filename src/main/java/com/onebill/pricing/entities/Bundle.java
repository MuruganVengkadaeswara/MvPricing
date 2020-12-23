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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Data;

@Entity
@Data
@Table(name = "bundles")
public class Bundle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bundle_id")
	private int bundleId;

	@NotNull
	@Column(name = "bundle_name", unique = true)
	@Size(max = 25)
	private String bundleName;

	// @NotNull
	// @Column(name = "validity_days")
	// private int validityDays;

	@NotNull
	@Size(max = 20)
	private String bundleType;

	@OneToMany(mappedBy = "bundle", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<BundleProduct> bundleProducts;

}
