package com.onebill.pricing.daotest;

import static org.junit.Assert.*;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.onebill.pricing.PricingAppConfiguration;
import com.onebill.pricing.dao.BundleDao;
import com.onebill.pricing.dao.BundleProductDao;
import com.onebill.pricing.dao.ProductDao;
import com.onebill.pricing.entities.Bundle;
import com.onebill.pricing.entities.BundleProduct;
import com.onebill.pricing.entities.Product;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PricingAppConfiguration.class)
@WebAppConfiguration
public class TestBundleProductDao {

	@Autowired
	BundleDao bDao;

	@Autowired
	BundleProductDao bpDao;

	@Autowired
	ProductDao pDao;

	@Autowired
	ApplicationContext context;

	@Test
	public void testAddBundleProduct() {

		Bundle b = addDummyBundle("dummy bundle", 30);
		Product p = addDummyProduct("dummy product");

		BundleProduct bp = new BundleProduct();
		bp.setBundleId(b.getBundleId());
		bp.setProductId(p.getProductId());

		bp = bpDao.addBundleProduct(bp);

		assertTrue(bp.getBpId() > 0);
		assertEquals(b.getBundleId(), bp.getBundleId());
		assertEquals(p.getProductId(), bp.getProductId());
	}

	@Test(expected = PersistenceException.class)
	public void testAddDuplicateProducts() {

		Bundle b = addDummyBundle("dummy bundle", 30);
		Product p = addDummyProduct("dummy product");

		BundleProduct bp = new BundleProduct();
		bp.setBundleId(b.getBundleId());
		bp.setProductId(p.getProductId());
		bpDao.addBundleProduct(bp);

		BundleProduct bp1 = new BundleProduct();
		bp1.setBundleId(b.getBundleId());
		bp1.setProductId(p.getProductId());
		bpDao.addBundleProduct(bp1);

	}

	@Test
	public void removeBundleProduct() {
		Bundle b = addDummyBundle("dummy bundle", 30);
		Product p = addDummyProduct("dummy product");

		BundleProduct bp = new BundleProduct();
		bp.setBundleId(b.getBundleId());
		bp.setProductId(p.getProductId());

		bp = bpDao.addBundleProduct(bp);

		BundleProduct bp1 = bpDao.removeBundleProduct(bp.getBpId());

		assertEquals(b.getBundleId(), bp1.getBundleId());
		assertEquals(p.getProductId(), bp1.getProductId());
	}

	@Test
	public void getBundleProductById() {
		Bundle b = addDummyBundle("dummy bundle", 30);
		Product p = addDummyProduct("dummy product");

		BundleProduct bp = new BundleProduct();
		bp.setBundleId(b.getBundleId());
		bp.setProductId(p.getProductId());

		bp = bpDao.addBundleProduct(bp);

		BundleProduct bp1 = bpDao.getBundleProductById(bp.getBpId());

		assertEquals(b.getBundleId(), bp1.getBundleId());
		assertEquals(p.getProductId(), bp1.getProductId());

	}

	public Bundle addDummyBundle(String name, int days) {
		Bundle bundle = new Bundle();
		bundle.setBundleName(name);
		bundle.setValidityDays(days);
		return bDao.addBundle(bundle);
	}

	public Product addDummyProduct(String name) {
		Product p = new Product();
		p.setProductName(name);
		return pDao.addProduct(p);
	}

}
