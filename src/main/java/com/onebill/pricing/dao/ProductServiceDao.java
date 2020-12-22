package com.onebill.pricing.dao;

import java.util.List;

import com.onebill.pricing.entities.Product;
import com.onebill.pricing.entities.ProductService;
import com.onebill.pricing.entities.Service;

public interface ProductServiceDao {

	ProductService addProductService(ProductService prodService);

	ProductService removeProductServiceById(int psId);

	ProductService getProductServiceById(int psId);

	ProductService updateProductService(ProductService prodService);

	List<Service> getAllServicesOfProduct(int prodId);

	List<Product> getAllProductbyServiceId(int servId);

	List<ProductService> getAllProductServices();

	List<ProductService> getAllProductServicesByProductId(int prodId);

	List<ProductService> removeAllProductServicesByProductId(int prodId);

	List<ProductService> getAllProductServicesByServiceId(int servId);

}
