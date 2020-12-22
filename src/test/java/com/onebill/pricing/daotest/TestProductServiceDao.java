package com.onebill.pricing.daotest;

import static org.junit.Assert.*;

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
import com.onebill.pricing.dao.ProductDao;
import com.onebill.pricing.dao.ProductServiceDao;
import com.onebill.pricing.dao.ServiceDao;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.entities.ProductService;
import com.onebill.pricing.entities.Service;
import com.sun.istack.logging.Logger;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PricingAppConfiguration.class)
@WebAppConfiguration
public class TestProductServiceDao {

	@Autowired
	ProductDao prodDao;

	@Autowired
	ServiceDao servDao;

	@Autowired
	ProductServiceDao prodServDao;

	@Autowired
	ApplicationContext context;

	Logger logger = Logger.getLogger(TestProductServiceDao.class);

	@Test
	public void testAddProductService() {

		Product product = addDummyProduct();
		Service service = addDummyService();

		ProductService ps = addDummyProductService(product.getProductId(), service.getServiceId(), 0.5, 400, "mb");

		ps = prodServDao.addProductService(ps);

		assertTrue(ps.getPsId() > 0);
		assertEquals(product.getProductId(), ps.getProductId());
		assertEquals(service.getServiceId(), ps.getServiceId());
		assertEquals(0.5, ps.getServicePrice(), 0);
		assertEquals(400, ps.getFreeUnits());

	}

	@Test(expected = PersistenceException.class)
	public void testAddDuplicateProductService() {

		Product product = addDummyProduct();
		Service service = addDummyService();

		addDummyProductService(product.getProductId(), service.getServiceId(), 0.5, 400, "mb");

		addDummyProductService(product.getProductId(), service.getServiceId(), 0.5, 400, "mb");

	}

	@Test
	public void testUpdateProductService() {

		Product product = addDummyProduct();
		Service service = addDummyService();

		ProductService ps = addDummyProductService(product.getProductId(), service.getServiceId(), 0.5, 400, "mb");

		ps.setFreeUnits(300);
		ps.setServicePrice(0.2);
		ps = prodServDao.updateProductService(ps);

		assertEquals(300, ps.getFreeUnits());
		assertEquals(0.2, ps.getServicePrice(), 0);

	}

	@Test
	public void testGetProductServiceById() {

		Product product = addDummyProduct();
		Service service = addDummyService();

		ProductService ps = addDummyProductService(product.getProductId(), service.getServiceId(), 0.5, 400, "mb");

		ProductService ps1 = prodServDao.getProductServiceById(ps.getPsId());

		assertNotNull(ps1);

	}

	@Test
	public void testGetAllProductServiceWithEmptyDB() {
		assertTrue(prodServDao.getAllProductServices().isEmpty());
	}

	@Test
	public void testRemoveProductServiceByProductId() {
		Product p = addDummyProduct();
		Service s = addDummyService();

		ProductService ps = addDummyProductService(p.getProductId(), s.getServiceId(), 400, 1000, "mins");

		prodServDao.removeAllProductServicesByProductId(p.getProductId());

	}

	public Product addDummyProduct() {
		Product product = new Product();
		product.setProductName("dummy product");
		return prodDao.addProduct(product);
	}

	public Service addDummyService() {
		Service service = new Service();
		service.setServiceName("dummy service");
		return servDao.addService(service);
	}

	public ProductService addDummyProductService(int productId, int serviceId, double price, long freeUnits,
			String unitType) {
		ProductService ps = new ProductService();
		ps.setProductId(productId);
		ps.setServiceId(serviceId);
		ps.setServicePrice(price);
		ps.setFreeUnits(freeUnits);
		ps.setUnitType(unitType);
		return prodServDao.addProductService(ps);
	}

}
