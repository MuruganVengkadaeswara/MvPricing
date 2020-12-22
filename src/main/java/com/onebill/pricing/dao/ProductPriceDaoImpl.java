package com.onebill.pricing.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.onebill.pricing.entities.ProductPrice;

@Repository
public class ProductPriceDaoImpl implements ProductPriceDao {

	@PersistenceContext
	EntityManager manager;

	@Override
	@Transactional
	public ProductPrice addProductPrice(ProductPrice price) {
		manager.persist(price);
		return price;
	}

	@Override
	@Transactional
	public ProductPrice updateProductPrice(ProductPrice price) {
		ProductPrice price1 = manager.find(ProductPrice.class, price.getProdPriceId());
		if (price1 != null) {
			BeanUtils.copyProperties(price, price1);
			return price1;
		} else {
			return null;
		}
	}

	@Override
	public ProductPrice getProductPriceById(int productId) {
		TypedQuery<ProductPrice> query = manager.createQuery("FROM ProductPrice where productId= :id",
				ProductPrice.class);
		query.setParameter("id", productId);
		List<ProductPrice> list = query.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);

		} else {
			return null;
		}

	}

	@Override
	public ProductPrice getProductPrice(int productPriceId) {
		return manager.find(ProductPrice.class, productPriceId);
	}

	@Override
	@Transactional
	public ProductPrice removeProductPriceById(int prodId) {
		TypedQuery<ProductPrice> query = manager.createQuery("FROM ProductPrice where productId= :id",
				ProductPrice.class);
		query.setParameter("id", prodId);

		if (!query.getResultList().isEmpty()) {
			ProductPrice p = query.getResultList().get(0);
			manager.remove(p);
			return p;
		}
		else {
			
			return null;
		}
	}

}
