package com.onebill.pricing.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Data
@Table(name = "bundle_products", uniqueConstraints = @UniqueConstraint(columnNames = { "bundle_id", "product_id" }))
public class BundleProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bp_id")
	private int bpId;

	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
	private Product product;

	@Column(name = "product_id")
	private int productId;

	@ManyToOne
	@JoinColumn(name = "bundle_id", referencedColumnName = "bundle_id", insertable = false, updatable = false)
	private Bundle bundle;

	@Column(name = "bundle_id")
	private int bundleId;

}
