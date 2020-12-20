package com.onebill.pricing.daotest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
import com.onebill.pricing.dao.AdditionalPriceDao;
import com.onebill.pricing.dao.ProductDao;
import com.onebill.pricing.entities.AdditionalPrice;
import com.onebill.pricing.entities.Product;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PricingAppConfiguration.class)
@WebAppConfiguration
public class TestAdditionalPriceDao {

	@Autowired
	AdditionalPriceDao addlDao;

	@Autowired
	ProductDao prodDao;

	@Autowired
	ApplicationContext context;

	@Test
	public void testAddAddlPrice() {

		Product p = addDummyProduct();

		AdditionalPrice price = new AdditionalPrice();

		price.setPrice(300);
		price.setDescription("dummy description");
		price.setProductId(p.getProductId());

		price = addlDao.addAddlPrice(price);

		assertTrue(price.getAddlPriceId() > 0);
		assertEquals(300, price.getPrice(), 0);
		assertEquals("dummy description", price.getDescription());

	}

	@Test(expected = PersistenceException.class)
	public void testAddAddlPriceWithoutProduct() {
		
		AdditionalPrice price = new AdditionalPrice();

		price.setPrice(300);
		price.setDescription("dummy description");

		price = addlDao.addAddlPrice(price);
	}

	@Test
	public void testUpdateAddlPrice() {

		Product p = addDummyProduct();

		AdditionalPrice price = new AdditionalPrice();

		price.setPrice(300);
		price.setDescription("dummy description");
		price.setProductId(p.getProductId());

		price = addlDao.addAddlPrice(price);

		price.setPrice(400);
		price.setDescription("updated dummy description");

		AdditionalPrice price1 = addlDao.updateAddlPrice(price);

		assertEquals(400, price1.getPrice(), 0);
		assertEquals("updated dummy description", price1.getDescription());
	}

	@Test
	public void testRemoveAddlPrice() {
		Product p = addDummyProduct();

		AdditionalPrice price = new AdditionalPrice();

		price.setPrice(300);
		price.setDescription("dummy description");
		price.setProductId(p.getProductId());

		price = addlDao.addAddlPrice(price);

		AdditionalPrice price1 = addlDao.removeAddlPriceById(price.getAddlPriceId());

		assertEquals(price.getAddlPriceId(), price1.getAddlPriceId());
		assertEquals("dummy description", price1.getDescription());
	}

	@Test
	public void testGetAddlPriceById() {

		Product p = addDummyProduct();

		AdditionalPrice price = new AdditionalPrice();

		price.setPrice(300);
		price.setDescription("dummy description");
		price.setProductId(p.getProductId());

		price = addlDao.addAddlPrice(price);

		AdditionalPrice price1 = addlDao.getAddlPriceById(price.getAddlPriceId());

		assertEquals(price.getAddlPriceId(), price1.getAddlPriceId());
		assertEquals(price.getAddlPriceId(), price1.getAddlPriceId());
		assertEquals("dummy description", price1.getDescription());
		assertEquals(300, price1.getPrice(), 0);

	}

	@Test
	public void testGetAllAdditionalPricesByProductId() {

		Product p = addDummyProduct();

		AdditionalPrice price = new AdditionalPrice();

		price.setPrice(300);
		price.setDescription("dummy description");
		price.setProductId(p.getProductId());

		addlDao.addAddlPrice(price);

		AdditionalPrice price1 = new AdditionalPrice();

		price1.setPrice(300);
		price1.setDescription("dummy description");
		price1.setProductId(p.getProductId());

		addlDao.addAddlPrice(price1);

		List<AdditionalPrice> list = addlDao.getAddlPriceByProductId(p.getProductId());

		assertEquals(2, list.size());

	}

	public Product addDummyProduct() {
		Product product = new Product();
		product.setProductName("dummy product");
		return prodDao.addProduct(product);
	}

}
