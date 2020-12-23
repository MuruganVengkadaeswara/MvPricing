package com.onebill.pricing.servicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.transaction.Transactional;

import org.jboss.logging.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.onebill.pricing.PricingAppConfiguration;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ProductServiceDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.exceptions.PricingConflictsException;
import com.onebill.pricing.exceptions.PricingException;
import com.onebill.pricing.services.ProductManagerService;
import com.onebill.pricing.services.ServiceManagerService;

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

	@Autowired
	ServiceManagerService smservice;

	Logger logger = Logger.getLogger(TestProductManagerService.class);

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void addDuplicateProduct() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("The product With name dummy already exists");
		addDummyProduct("dummy");
		addDummyProduct("dummy");

	}

	@Test
	public void addProductWithInvalidName() {
		expectedEx.expect(PricingException.class);
		addDummyProduct("$^kjdhfgksjdb89(*^&U&((");
	}

	@Test
	public void addProductWithMoreThan25Chars() {
		expectedEx.expect(PricingException.class);
		addDummyProduct("sdjfhaskjdhfkjahsdfjhjksahdfkjhasjdfhkjsahdkfjhjsadfhkjsfkjsh");
	}

	@Test
	public void addProductWithLessThan2Chars() {
		expectedEx.expect(PricingException.class);
		addDummyProduct("s");
	}

	@Test
	public void testGetNonExistingProduct() throws NotFoundException {
		expectedEx.expect(NotFoundException.class);
		service.getProduct(636363);
	}

	@Test
	public void removeProductWithInvalidId() throws NotFoundException {
		expectedEx.expect(PricingException.class);
		expectedEx.expectMessage("Product Id must be greater than 0");
		service.removeProductById(-999);
	}

	@Test
	public void removeProductThatDoesntExist() throws NotFoundException {
		expectedEx.expect(NotFoundException.class);
		service.removeProductById(999999);
	}

	@Test
	public void testGetProductByName() throws NotFoundException {
		addDummyProduct("dummy");
		ProductDto dto = service.getProductByName("dummy");
		assertEquals("dummy", dto.getProductName());
		assertTrue(dto.getProductId() > 0);
	}

	@Test
	public void testGetNonExistingProductByName() throws NotFoundException {
		expectedEx.expect(NotFoundException.class);
		service.getProductByName("NON EXISTING PRODUCT");
	}

	@Test
	public void testUpdateProductWithInvalidName() throws NotFoundException {
		expectedEx.expect(PricingConflictsException.class);
		ProductDto dto = addDummyProduct("Dummy");
		dto.setProductName("**03984%%##@@#$!!22342rsvd");
		service.updateProduct(dto);
	}

	@Test
	public void testUpdateNonExistingProduct() throws NotFoundException {
		expectedEx.expect(NotFoundException.class);
		ProductDto dto = new ProductDto();
		dto.setProductId(999);
		dto.setProductName("NON EXISTING PRODUCT");
		service.updateProduct(dto);
	}

	@Test
	public void testDeleteProductWithServices() throws NotFoundException {
		expectedEx.expect(NotFoundException.class);
		ProductDto p = addDummyProduct("dummy product");
		ServiceDto s = addDummyService("dummy service");
		logger.info(p);
		ProductServiceDto ps = addDummyProdService(p.getProductId(), s.getServiceId());
		service.removeProductById(p.getProductId());
		assertNull(service.getProductService(ps.getPsId()));
		assertNull(service.getProduct(p.getProductId()));

	}

	// @Test
	// public void testAddProduct() {
	// ProductDto p = addDummyProduct("dummy");
	// assertTrue(p.getProductId() > 0);
	// assertEquals("dummy", p.getProductName());
	// }
	//
	// @Test
	// public void testUpdateProduct() {
	// ProductDto p = addDummyProduct("dummy");
	// p.setProductName("updated dummy");
	// p = service.updateProduct(p);
	// assertEquals("updated dummy", p.getProductName());
	// }
	//
	// @Test
	// public void testRemoveProductById() {
	// ProductDto p = addDummyProduct("dummy");
	// p = service.removeProductById(p.getProductId());
	// assertNull(service.getProduct(p.getProductId()));
	// }
	//
	// @Test
	// public void testGetProductById() {
	// ProductDto p = addDummyProduct("dummy");
	// ProductDto p1 = service.getProduct(p.getProductId());
	//
	// assertEquals(p.getProductName(), p1.getProductName());
	// }
	//
	// @Test
	// public void testGetAllProducts() throws NotFoundException {
	// addDummyProduct("dummy");
	// List<ProductDto> list = service.getAllProducts();
	// assertTrue(!list.isEmpty());
	// }
	//
	// // product price
	//
	// @Test
	// public void addProductPrice() {
	// ProductDto p = addDummyProduct("dummy");
	// ProductPriceDto price = new ProductPriceDto();
	//
	// price.setPrice(400);
	// price.setProductId(p.getProductId());
	//
	// price = service.addProductPrice(price);
	//
	// assertEquals(400, price.getPrice(), 0);
	// }
	//
	// @Test
	// public void updateProductPrice() {
	// ProductDto p = addDummyProduct("dummy");
	// ProductPriceDto price = new ProductPriceDto();
	//
	// price.setPrice(400);
	// price.setProductId(p.getProductId());
	//
	// price = service.addProductPrice(price);
	// price.setPrice(300);
	//
	// price = service.updateProductPrice(price);
	// assertEquals(300, price.getPrice(), 0);
	//
	// }

	public ServiceDto addDummyService(String name) {
		ServiceDto s = new ServiceDto();
		s.setServiceName(name);
		return smservice.addService(s);
	}

	public ProductServiceDto addDummyProdService(int productId, int serviceId) {
		ProductServiceDto ps = new ProductServiceDto();
		ps.setProductId(productId);
		ps.setServiceId(serviceId);
		ps.setFreeUnits(100);
		ps.setUnitType("mb");
		ps.setServicePrice(0.5);
		return service.addProductService(ps);
	}

	public ProductDto addDummyProduct(String name) {
		ProductDto p = new ProductDto();
		p.setProductName(name);
		return service.addProduct(p);
	}

}
