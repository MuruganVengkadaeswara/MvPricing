package com.onebill.pricing.daotest;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.onebill.pricing.PricingAppConfiguration;
import com.onebill.pricing.dao.ProductDao;
import com.onebill.pricing.dao.ProductPriceDao;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.entities.ProductPrice;
import com.sun.istack.logging.Logger;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PricingAppConfiguration.class)
@WebAppConfiguration
public class TestProductDao {

	// default rollback true

	Logger logger = Logger.getLogger(TestProductDao.class);

	@Autowired
	private ProductDao dao;

	@Autowired
	private ProductPriceDao pdao;

	@Autowired
	ApplicationContext context;

	@Test
	public void testGetProduct() {
		Product p = addDummyProduct("Dummy Product");
		assertEquals("Dummy Product", dao.getProduct(p.getProductId()).getProductName());
	}

	@Test
	public void testAddProduct() {
		Product p = addDummyProduct("dummy product");
		logger.info(p.toString());
		assertEquals("dummy product", p.getProductName());
		assertTrue(p.getProductId() > 0);
	}

	@Test(expected = PersistenceException.class)
	public void testAddDuplicateProduct() {
		addDummyProduct("dummy product");
		addDummyProduct("dummy product");
	}

	@Test
	public void testRemoveProduct() {
		Product p = addDummyProduct("dummy product");
		Product p1 = dao.removeProductById(p.getProductId());
		assertEquals("dummy product", p1.getProductName());
	}

	@Test
	public void removeProductWithPrice() {

		Product p = addDummyProduct("dummy product");

		ProductPrice price = new ProductPrice();
		price.setPrice(400);
		price.setProductId(p.getProductId());

		price = pdao.addProductPrice(price);

		Product p1 = dao.removeProductById(p.getProductId());
		logger.info(p1.toString());

		assertEquals("dummy product", p1.getProductName());
		assertNull(pdao.getProductPrice(price.getProdPriceId()));

	}


	@Test
	public void testUpdateProduct() {
		Product p = addDummyProduct("dummy product");
		p.setProductName("updated dummy product");
		Product p1 = dao.updateProduct(p);
		assertEquals("updated dummy product", p1.getProductName());
	}

	@Test
	public void testGetAllProducts() {
		addDummyProduct("dummy 1");
		addDummyProduct("dummy 2");

		List<Product> list = dao.getAllProducts();
		assertTrue(!list.isEmpty());
	}

	public Product addDummyProduct(String name) {
		Product p = new Product();
		p.setProductName(name);
		return dao.addProduct(p);
	}

}
