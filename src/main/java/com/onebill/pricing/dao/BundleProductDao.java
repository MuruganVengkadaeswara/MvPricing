package com.onebill.pricing.dao;

import com.onebill.pricing.entities.BundleProduct;

public interface BundleProductDao {

	public BundleProduct addBundleProduct(BundleProduct bundleProduct);

	public BundleProduct removeBundleProduct(int id);
	
	public BundleProduct getBundleProductById(int id);
	

}
