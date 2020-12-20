package com.onebill.pricing.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.onebill.pricing.entities.BundleProduct;

@Repository
public class BundleProductDaoImpl implements BundleProductDao {

	@PersistenceContext
	EntityManager manager;

	@Override
	@Transactional
	public BundleProduct addBundleProduct(BundleProduct bundleProduct) {
		manager.persist(bundleProduct);
		return bundleProduct;
	}

	@Override
	public BundleProduct removeBundleProduct(int id) {
		BundleProduct bp = manager.find(BundleProduct.class, id);
		if (bp != null) {
			manager.remove(bp);
			return bp;
		}
		return null;
	}

	@Override
	public BundleProduct getBundleProductById(int id) {
		return manager.find(BundleProduct.class, id);
	}

}
