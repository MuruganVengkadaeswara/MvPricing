package com.onebill.pricing.servicetest;

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
import com.onebill.pricing.dto.BundleDto;
import com.onebill.pricing.dto.BundleProductDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.services.BundleManagerService;
import com.onebill.pricing.services.ProductManagerService;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PricingAppConfiguration.class)
@WebAppConfiguration
public class TestBundleManagerService {

	@Autowired
	ApplicationContext context;

	@Autowired
	ProductManagerService prodService;

	@Autowired
	BundleManagerService bundleService;

	@Test
	public void testAddBundle() {
		BundleDto bundle = addDummyBundle("dummy bundle", 100);
		assertTrue(bundle.getBundleId() > 0);
		assertEquals("dummy bundle", bundle.getBundleName());
		assertEquals(100, bundle.getValidityDays());
	}

	@Test
	public void testUpdateBundle() {
		BundleDto bundle = addDummyBundle("dummy bundle", 100);
		bundle.setValidityDays(200);

		bundle = bundleService.updateBundle(bundle);

		assertEquals(200, bundle.getValidityDays());
	}

	@Test
	public void testDeleteBundle() {
		BundleDto bundle = addDummyBundle("dummy bundle", 100);

		bundleService.removeBundel(bundle.getBundleId());

		assertNull(bundleService.getBundle(bundle.getBundleId()));
	}

	@Test
	public void testGetBundleById() {
		BundleDto bundle = addDummyBundle("dummy bundle", 100);

		BundleDto b = bundleService.getBundle(bundle.getBundleId());

		assertEquals("dummy bundle", b.getBundleName());
		assertEquals(100, b.getValidityDays());
	}

	@Test
	public void testAddBundleProduct() {
		BundleDto bundle = addDummyBundle("dummy bundle", 100);
		ProductDto product = addDummyProduct("dummy product");

		BundleProductDto bp = addDummyBundleProduct(bundle.getBundleId(), product.getProductId());

		assertTrue(bp.getBpId() > 0);
		assertEquals(bundle.getBundleId(), bp.getBundleId());
		assertEquals(product.getProductId(), bp.getProductId());
	}

	@Test(expected = PersistenceException.class)
	public void testAddDuplicateProductToBundle() {
		BundleDto bundle = addDummyBundle("dummy bundle", 100);
		ProductDto product = addDummyProduct("dummy product");
		addDummyBundleProduct(bundle.getBundleId(), product.getProductId());
		addDummyBundleProduct(bundle.getBundleId(), product.getProductId());
	}

	public BundleDto addDummyBundle(String name, int validityDays) {
		BundleDto b = new BundleDto();
		b.setBundleName(name);
		b.setValidityDays(validityDays);
		return bundleService.addBundle(b);
	}

	public ProductDto addDummyProduct(String name) {
		ProductDto p = new ProductDto();
		p.setProductName(name);
		return prodService.addProduct(p);
	}

	public BundleProductDto addDummyBundleProduct(int bundleId, int productId) {
		BundleProductDto b = new BundleProductDto();
		b.setBundleId(bundleId);
		b.setProductId(productId);
		return bundleService.addBundleProduct(b);
	}

}
