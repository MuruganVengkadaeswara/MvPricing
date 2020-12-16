package com.onebill.pricing.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;

import com.onebill.pricing.entities.Product;

public class ProductDaoImpl implements ProductDao {

	@PersistenceContext
	EntityManager manager;

	@Override
	@Transactional
	public Product addProduct(Product product) {
		manager.persist(product);
		return product;
	}

	@Override
	@Transactional
	public Product removeProductById(int productId) {
		Product product = manager.find(Product.class, productId);
		if (product != null) {
			manager.remove(product);
			return product;
		} else {
			return null;
		}

	}

	@Override
	@Transactional
	public Product updateProduct(Product product) {
		Product product1 = manager.find(Product.class, product.getProductId());
		if (product1 != null) {
			BeanUtils.copyProperties(product, product1);
			return product1;
		} else {
			return null;
		}
	}

	@Override
	public Product getProduct(int productId) {
		return manager.find(Product.class, productId);
	}

}
