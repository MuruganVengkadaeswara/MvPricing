package com.onebill.pricing.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onebill.pricing.entities.Bundle;
import com.onebill.pricing.entities.BundleProduct;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.entities.ProductPrice;

@Repository
public class BundleDaoImpl implements BundleDao {

	@PersistenceContext
	EntityManager manager;

	@Override
	@Transactional
	public Bundle addBundle(Bundle bundle) {
		manager.persist(bundle);
		return bundle;
	}

	@Override
	@Transactional
	public Bundle updateBundle(Bundle bundle) {
		Bundle bundle1 = manager.find(Bundle.class, bundle.getBundleId());
		if (bundle1 != null) {
			BeanUtils.copyProperties(bundle, bundle1);
			manager.persist(bundle1);
			return bundle1;
		} else {
			return null;
		}
	}

	@Override
	public Bundle getBundle(int bundleId) {
		return manager.find(Bundle.class, bundleId);
	}

	@Override
	@Transactional
	public Bundle removeBundle(int bundleId) {
		Bundle bundle = manager.find(Bundle.class, bundleId);
		if (bundle != null) {
			manager.remove(bundle);
			return bundle;
		} else {

			return null;
		}
	}

	@Override
	public List<Bundle> getAllBundles() {
		TypedQuery<Bundle> query = manager.createQuery("FROM Bundle", Bundle.class);
		return query.getResultList();
	}

	@Override
	public Bundle getBundleByName(String text) {
		TypedQuery<Bundle> query = manager.createQuery("FROM Bundle where bundleName= :name", Bundle.class);
		query.setParameter("name", text);
		List<Bundle> list = query.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Bundle> searchBundleByName(String text) {
		TypedQuery<Bundle> query = manager.createQuery("FROM Bundle where bundleName like :name", Bundle.class);
		query.setParameter("name", "%" + text + "%");
		return query.getResultList();
	}

}
