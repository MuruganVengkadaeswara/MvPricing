package com.onebill.pricing.dao;

import com.onebill.pricing.entities.ProductPrice;

public interface ProductPriceDao {
	
	ProductPrice addProductPrice(ProductPrice price);
	
	ProductPrice updateProductPrice(ProductPrice price);
	
	ProductPrice getProductPrice(int productId);
	
	
	

}
