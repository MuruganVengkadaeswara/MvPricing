package com.onebill.pricing.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Repository;

import com.onebill.pricing.entities.BundleProduct;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.entities.ProductPrice;
import com.onebill.pricing.exceptions.PricingException;

@Repository
public class BundleProductDaoImpl implements BundleProductDao {

	@PersistenceContext
	EntityManager manager;

	Logger logger = Logger.getLogger(BundleProductDaoImpl.class);

	@Override
	@Transactional
	public BundleProduct addBundleProduct(BundleProduct bundleProduct) {

		TypedQuery<ProductPrice> query = manager.createQuery("FROM ProductPrice where productId= :id",
				ProductPrice.class);
		query.setParameter("id", bundleProduct.getProductId());
		if (!query.getResultList().isEmpty()) {
			manager.persist(bundleProduct);
			return bundleProduct;

		} else {
			throw new PricingException("The product to be added has no Price ! please update the price");
		}

	}

	@Override
	@Transactional
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

	@Override
	@Transactional
	public List<BundleProduct> removeBundleProductByProductId(int id) {
		TypedQuery<Integer> query = manager.createQuery("select bpId FROM BundleProduct where productId= :id",
				Integer.class);
		query.setParameter("id", id);
		List<Integer> list = query.getResultList();
		logger.info(list.toString());
		List<BundleProduct> blist = new ArrayList<>();
		if (!list.isEmpty()) {
			for (int e : list) {
				blist.add(manager.find(BundleProduct.class, e));
				manager.remove(manager.find(BundleProduct.class, e));
			}
		}

		return blist;
	}

	@Override
	public List<Product> getAllProductsOfbundle(int bundleId) {

		TypedQuery<BundleProduct> query = manager.createQuery("FROM BundleProduct where bundleId= :id",
				BundleProduct.class);
		query.setParameter("id", bundleId);
		List<Product> list = new ArrayList<>();
		List<BundleProduct> prodId = query.getResultList();
		if (!prodId.isEmpty()) {
			for (BundleProduct p : prodId) {
				list.add(manager.find(Product.class, p.getProductId()));
			}
		}
		return list;
	}

	@Override
	@Transactional
	public BundleProduct removeProductOfBundle(int productId, int bundleId) {

		TypedQuery<Integer> query = manager
				.createQuery("select bpId FROM BundleProduct where bundleId= :bid and productId= :pid", Integer.class);
		query.setParameter("pid", productId);
		query.setParameter("bid", bundleId);
		int id = query.getResultList().get(0);
		logger.info(Integer.toString(id));
		BundleProduct p = manager.find(BundleProduct.class, id);
		if (p != null) {
			manager.remove(manager.find(BundleProduct.class, id));
			return p;
		} else {
			return null;
		}

	}

}
