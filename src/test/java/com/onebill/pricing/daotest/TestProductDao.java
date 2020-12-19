package com.onebill.pricing.daotest;

import static org.junit.Assert.*;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.onebill.pricing.PricingAppConfiguration;
import com.onebill.pricing.dao.ProductDao;
import com.onebill.pricing.entities.Product;
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
	ApplicationContext context;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetProduct() {
		Product p = dao.getProduct(1);
		assertNotNull(p);
	}

	@Test
	public void testAddProduct() {
		Product p = new Product();
		p.setProductName("Test Product");
		assertNotNull(dao.addProduct(p));
	}

	@Test
	public void testRemoveProduct() {
		assertNotNull(dao.removeProductById(2));
	}

	@Test
	public void testUpdateProduct() {
		Product p = new Product();
		p.setProductId(2);
		p.setProductName("Jio Monthly");
		Product p1 = dao.updateProduct(p);
		assertEquals(p1, p);
	}
	
	@Test
	public void testGetAllProducts() {
		List<Product> list = dao.getAllProducts();
		assertNotNull(list);
	}

}
