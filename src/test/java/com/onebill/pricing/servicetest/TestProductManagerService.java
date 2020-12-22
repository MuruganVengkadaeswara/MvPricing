package com.onebill.pricing.servicetest;

import static org.junit.Assert.*;

import java.util.List;

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
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ProductPriceDto;
import com.onebill.pricing.services.ProductManagerService;

import javassist.NotFoundException;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PricingAppConfiguration.class)
@WebAppConfiguration
public class TestProductManagerService {

	@Autowired
	ApplicationContext context;

	@Autowired
	ProductManagerService service;

	Logger logger = Logger.getLogger(TestProductManagerService.class);

	@Test
	public void testAddProduct() {
		ProductDto p = addDummyProduct("dummy");
		assertTrue(p.getProductId() > 0);
		assertEquals("dummy", p.getProductName());
	}

	@Test
	public void testUpdateProduct() {
		ProductDto p = addDummyProduct("dummy");
		p.setProductName("updated dummy");
		p = service.updateProduct(p);
		assertEquals("updated dummy", p.getProductName());
	}

	@Test
	public void testRemoveProductById() {
		ProductDto p = addDummyProduct("dummy");
		p = service.removeProductById(p.getProductId());
		assertNull(service.getProduct(p.getProductId()));
	}

	@Test
	public void testGetProductById() {
		ProductDto p = addDummyProduct("dummy");
		ProductDto p1 = service.getProduct(p.getProductId());

		assertEquals(p.getProductName(), p1.getProductName());
	}

	@Test
	public void testGetAllProducts() throws NotFoundException {
		addDummyProduct("dummy");
		List<ProductDto> list = service.getAllProducts();
		assertTrue(!list.isEmpty());
	}

	// product price

	@Test
	public void addProductPrice() {
		ProductDto p = addDummyProduct("dummy");
		ProductPriceDto price = new ProductPriceDto();

		price.setPrice(400);
		price.setProductId(p.getProductId());

		price = service.addProductPrice(price);

		assertEquals(400, price.getPrice(), 0);
	}

	@Test
	public void updateProductPrice() {
		ProductDto p = addDummyProduct("dummy");
		ProductPriceDto price = new ProductPriceDto();

		price.setPrice(400);
		price.setProductId(p.getProductId());

		price = service.addProductPrice(price);
		price.setPrice(300);

		price = service.updateProductPrice(price);
		assertEquals(300, price.getPrice(), 0);

	}

	public ProductDto addDummyProduct(String name) {
		ProductDto p = new ProductDto();
		p.setProductName(name);
		return service.addProduct(p);
	}

}
