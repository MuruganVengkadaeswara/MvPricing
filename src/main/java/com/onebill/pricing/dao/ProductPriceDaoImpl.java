package com.onebill.pricing.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.onebill.pricing.entities.ProductPrice;

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
	public ProductPrice getProductPrice(int productId) {
		TypedQuery<ProductPrice> query = manager.createQuery("FROM ProductPrice where prodPriceId= :id",
				ProductPrice.class);
		query.setParameter("id", productId);
		List<ProductPrice> list = query.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);

		} else {
			return null;
		}

	}

}
