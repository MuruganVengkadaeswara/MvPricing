package com.onebill.pricing.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.onebill.pricing.entities.AdditionalPrice;

@Repository
public class AdditionalPriceDaoImpl implements AdditionalPriceDao {

	@PersistenceContext
	EntityManager manager;

	@Override
	@Transactional
	public AdditionalPrice addAddlPrice(AdditionalPrice price) {
		manager.persist(price);
		return price;
	}

	@Override
	@Transactional
	public AdditionalPrice updateAddlPrice(AdditionalPrice price) {
		AdditionalPrice price1 = manager.find(AdditionalPrice.class, price.getAddlPriceId());
		if (price1 != null) {
			BeanUtils.copyProperties(price, price1);
			return price1;
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public AdditionalPrice removeAddlPriceById(int id) {
		AdditionalPrice price = manager.find(AdditionalPrice.class, id);
		if (price != null) {
			manager.remove(price);
			return price;
		} else {
			return null;
		}
	}

	@Override
	public AdditionalPrice getAddlPriceById(int id) {
		return manager.find(AdditionalPrice.class, id);
	}

	@Override
	public List<AdditionalPrice> getAddlPriceByProductId(int prodId) {

		TypedQuery<AdditionalPrice> query = manager.createQuery("FROM AdditionalPrice where productId= :id", AdditionalPrice.class);
		query.setParameter("id", prodId);
		List<AdditionalPrice> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		} else {
			return null;
		}
	}

}
