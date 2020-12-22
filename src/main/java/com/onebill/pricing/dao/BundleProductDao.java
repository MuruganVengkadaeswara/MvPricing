package com.onebill.pricing.dao;

import java.util.List;

import com.onebill.pricing.entities.BundleProduct;
import com.onebill.pricing.entities.Product;

public interface BundleProductDao {

	public BundleProduct addBundleProduct(BundleProduct bundleProduct);

	public BundleProduct removeBundleProduct(int id);

	public BundleProduct getBundleProductById(int id);

	public List<BundleProduct> removeBundleProductByProductId(int id);

	public List<Product> getAllProductsOfbundle(int bundleId);
}
