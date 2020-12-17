package com.onebill.pricing.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;

import com.onebill.pricing.entities.ExtraPrice;

public class ExtraPriceDaoImpl implements ExtraPriceDao {

	@PersistenceContext
	EntityManager manager;

	@Override
	@Transactional
	public ExtraPrice addExtraPrice(ExtraPrice price) {
		manager.persist(price);
		return price;
	}

	@Override
	@Transactional
	public ExtraPrice updateExtraPrice(ExtraPrice price) {
		ExtraPrice price1 = manager.find(ExtraPrice.class, price.getExtraId());
		if (price1 != null) {
			BeanUtils.copyProperties(price, price1);
			return price1;
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public ExtraPrice removeExtraPriceById(int id) {
		ExtraPrice price = manager.find(ExtraPrice.class, id);
		if (price != null) {
			manager.remove(price);
			return price;
		} else {
			return null;
		}
	}

	@Override
	public ExtraPrice getExtraPriceById(int id) {
		return manager.find(ExtraPrice.class, id);
	}

	@Override
	public List<ExtraPrice> getExtraPriceByProductId(int prodId) {

		TypedQuery<ExtraPrice> query = manager.createQuery("FROM ExtraPrice where productId= : id", ExtraPrice.class);
		List<ExtraPrice> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		} else {
			return null;
		}
	}

}
