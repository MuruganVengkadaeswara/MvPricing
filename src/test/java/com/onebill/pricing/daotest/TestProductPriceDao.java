package com.onebill.pricing.daotest;

import static org.junit.Assert.*;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.onebill.pricing.PricingAppConfiguration;
import com.onebill.pricing.dao.ProductDao;
import com.onebill.pricing.dao.ProductPriceDao;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.entities.ProductPrice;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PricingAppConfiguration.class)
@WebAppConfiguration
public class TestProductPriceDao {

	@Autowired
	private ProductPriceDao prodPriceDao;

	@Autowired
	private ProductDao prodDao;

	@Autowired
	private ApplicationContext context;

	Logger logger = Logger.getLogger(TestProductPriceDao.class);

	@Test
	public void testAddPrice() {

		Product p = addDummyProduct("dummy product");

		ProductPrice price = addDummyProductprice(p.getProductId(), 399.99);

		assertTrue(price.getProdPriceId() > 0);
		assertEquals(p.getProductId(), price.getProductId());
		assertEquals(399.99, price.getPrice(), 0);
	}

	@Test(expected = PersistenceException.class)
	public void testAddPriceWithoutProduct() {

		ProductPrice price = new ProductPrice();
		price.setPrice(4000);
		prodPriceDao.addProductPrice(price);

	}

	@Test
	public void testUpdatePrice() {

		Product p = addDummyProduct("dummy product");

		ProductPrice price = addDummyProductprice(p.getProductId(), 399.99);

		price.setPrice(499.99);
		price = prodPriceDao.updateProductPrice(price);

		assertTrue(price.getProdPriceId() > 0);
		assertEquals(p.getProductId(), price.getProductId());
		assertEquals(499.99, price.getPrice(), 0);

	}

	@Test
	public void testGetProductPriceByProductId() {

		Product p = addDummyProduct("dummy product");

		addDummyProductprice(p.getProductId(), 399.99);

		ProductPrice actual = prodPriceDao.getProductPriceById(p.getProductId());

		assertEquals(399.99, actual.getPrice(), 0);
	}

	@Test
	public void testGetProductPriceByPriceId() {
		
		Product p = addDummyProduct("dummy product");

		ProductPrice price = addDummyProductprice(p.getProductId(), 399.99);

		ProductPrice actual = prodPriceDao.getProductPrice(price.getProdPriceId());

		assertEquals(399.99, actual.getPrice(), 0);
	}

	public Product addDummyProduct(String name) {
		Product product = new Product();
		product.setProductName(name);
		return prodDao.addProduct(product);
	}

	public ProductPrice addDummyProductprice(int productId, double prodPrice) {
		ProductPrice price = new ProductPrice();
		price.setProductId(productId);
		price.setPrice(prodPrice);
		return prodPriceDao.addProductPrice(price);

	}

}
