package com.onebill.pricing.dao;

import com.onebill.pricing.entities.Product;

public interface ProductDao {

	Product addProduct(Product product);

	Product removeProductById(int productId);

	Product updateProduct(Product product);

	Product getProduct(int productId);
	
	
}
