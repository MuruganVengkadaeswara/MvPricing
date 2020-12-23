package com.onebill.pricing.dao;

import java.util.List;

import com.onebill.pricing.entities.Product;

import javassist.NotFoundException;

public interface ProductDao {

	Product addProduct(Product product);

	Product removeProductById(int productId);

	Product updateProduct(Product product);

	Product getProduct(int productId);

	List<Product> getAllProducts();

	Product getProductByName(String text);

}
