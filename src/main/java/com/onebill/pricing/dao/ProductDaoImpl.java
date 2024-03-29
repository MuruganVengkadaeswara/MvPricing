package com.onebill.pricing.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.onebill.pricing.entities.Product;
import com.sun.istack.logging.Logger;

@Repository
public class ProductDaoImpl implements ProductDao {

	@PersistenceContext
	EntityManager manager;

	Logger logger = Logger.getLogger(ProductDaoImpl.class);

	@Override
	@Transactional
	public Product addProduct(Product product) {
		manager.persist(product);
		manager.flush();
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

	@Override
	public List<Product> getAllProducts() {
		List<Product> list;
		TypedQuery<Product> query = manager.createQuery("FROM Product", Product.class);
		list = query.getResultList();
		return list;
	}

	@Override
	public Product getProductByName(String text) {

		TypedQuery<Product> query = manager.createQuery("FROM Product where productName= :name", Product.class);
		query.setParameter("name", text);
		List<Product> list = query.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Product> searchProductsByName(String text) {
		TypedQuery<Product> query = manager.createQuery("FROM Product where productName like :text", Product.class);
		query.setParameter("text", "%" + text + "%");
		return query.getResultList();

	}

	

}
