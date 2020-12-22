package com.onebill.pricing.dao;

import com.onebill.pricing.entities.ProductPrice;

public interface ProductPriceDao {

	ProductPrice addProductPrice(ProductPrice price);

	ProductPrice updateProductPrice(ProductPrice price);

	ProductPrice getProductPriceById(int productId);

	ProductPrice getProductPrice(int productPriceId);

	ProductPrice removeProductPriceById(int prodId);

}
