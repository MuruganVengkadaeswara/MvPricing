package com.onebill.pricing.servicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.jboss.logging.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.mockito.Mockito.*;

import com.onebill.pricing.PricingAppConfiguration;
import com.onebill.pricing.dao.AdditionalPriceDao;
import com.onebill.pricing.dao.BundleProductDao;
import com.onebill.pricing.dao.ProductDao;
import com.onebill.pricing.dao.ProductPriceDao;
import com.onebill.pricing.dao.ProductServiceDao;
import com.onebill.pricing.dao.ServiceDao;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ProductPriceDto;
import com.onebill.pricing.dto.ProductServiceDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.entities.ProductService;
import com.onebill.pricing.entities.Service;
import com.onebill.pricing.exceptions.PricingConflictsException;
import com.onebill.pricing.exceptions.PricingException;
import com.onebill.pricing.exceptions.PricingNotFoundException;
import com.onebill.pricing.services.ProductManagerService;
import com.onebill.pricing.services.ProductManagerServiceImpl;
import com.onebill.pricing.services.ServiceManagerService;

import javassist.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class TestProductManagerService {

	@Mock
	private ProductDao prodDao;

	@Mock
	private ServiceDao servDao;

	@Mock
	private ProductServiceDao prodServDao;

	@Mock
	private ProductPriceDao priceDao;

	@Mock
	AdditionalPriceDao expDao;

	@Mock
	BundleProductDao bpDao;

	@InjectMocks
	private ProductManagerService prodservice = new ProductManagerServiceImpl();

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	Logger logger = Logger.getLogger(TestProductManagerService.class);

	@Autowired
	ModelMapper mapper;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddProductWithNullName() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Please Provide a product Name");
		ProductDto dto = new ProductDto();
		prodservice.addProduct(dto);

	}

	@Test
	public void addDuplicateProductName() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("The product with name Hello already exists");
		ProductDto dto = new ProductDto();
		dto.setProductName("Hello");
		Mockito.when(prodDao.getProductByName("Hello")).thenReturn(new Product());
		prodservice.addProduct(dto);

	}

	@Test
	public void addProductWithInvalidName() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Product Name must be letters numbers and spaces and be within 25 characters");
		ProductDto dto = new ProductDto();
		dto.setProductName("*(#^(*^@($*^$*(@$&(*$^");
		prodservice.addProduct(dto);
	}

	@Test
	public void testAddProductWithoutPrice() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("The product price must be greater than 0");
		ProductDto dto = new ProductDto();
		dto.setProductName("Airtel Monthly");
		prodservice.addProduct(dto);

	}

	@Test
	public void testAddProductWithNegativePrice() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("The product price must be greater than 0");
		ProductDto dto = new ProductDto();
		dto.setProductName("Airtel Monthly");
		ProductPriceDto price = new ProductPriceDto();
		price.setPrice(-400);
		dto.setPrice(price);
		prodservice.addProduct(dto);
	}

	@Test
	public void testAddProductWithNoServices() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Add Atleast one service");
		ProductDto dto = new ProductDto();
		dto.setProductName("Airtel Monthly");
		ProductPriceDto price = new ProductPriceDto();
		price.setPrice(499);
		dto.setPrice(price);
		prodservice.addProduct(dto);
	}

	@Test
	public void testAddProductWithDuplicateService() {

		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Trying to add Duplicate Service ! Please remove it");

		ProductDto dto = new ProductDto();
		dto.setProductName("Airtel Monthly");

		ProductPriceDto price = new ProductPriceDto();
		price.setPrice(499);
		dto.setPrice(price);

		ProductServiceDto service = new ProductServiceDto();
		service.setServiceId(1);

		List<ProductServiceDto> serviceList = new ArrayList<>();
		serviceList.add(service);
		serviceList.add(service);
		dto.setServices(serviceList);

		prodservice.addProduct(dto);
	}

	@Test
	public void testAddProductWithNonExistingService() {

		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("The service with Id 1 Doesn't exist");

		ProductDto dto = new ProductDto();
		dto.setProductName("Airtel Monthly");

		ProductPriceDto price = new ProductPriceDto();
		price.setPrice(499);
		dto.setPrice(price);

		ProductServiceDto service = new ProductServiceDto();
		service.setServiceId(1);

		List<ProductServiceDto> serviceList = new ArrayList<>();
		serviceList.add(service);
		dto.setServices(serviceList);

		prodservice.addProduct(dto);
	}

	@Test
	public void testRemoveProductWithNegativeId() {
		expectedEx.expect(PricingException.class);
		prodservice.removeProductById(-99);
	}

	@Test
	public void testRemoveNonExistingProduct() {
		expectedEx.expect(PricingNotFoundException.class);
		expectedEx.expectMessage("The product with id 4 is not found");
		Mockito.when(prodDao.removeProductById(4)).thenReturn(null);
		prodservice.removeProductById(4);
	}

	@Test
	public void updateProductWithNegativeProductId() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("The product id must be > 0 and name must contain only letters and spaces");
		ProductDto dto = new ProductDto();
		dto.setProductId(-99);
		dto.setProductName("Airtel yearly");
		prodservice.updateProduct(dto);
	}

	// @Test
	public void testUpdateNonExistingProduct() {

		// expectedEx.expect(PricingNotFoundException.class);
		ProductDto dto = new ProductDto();
		dto.setProductId(99);
		dto.setProductName("Airtel yearly");

		Product p = mapper.map(dto, Product.class);

		Mockito.when(prodDao.updateProduct(p)).thenReturn(null);

		prodservice.updateProduct(dto);

	}

	@Test
	public void testGetProductWithNegativeId() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Product Id must be greater than 0");
		prodservice.getProduct(-99);
	}

	@Test
	public void testGetNonExistingProduct() {

		expectedEx.expect(PricingNotFoundException.class);
		expectedEx.expectMessage("Product with id 4 doesnt exist");

		Mockito.when(prodDao.getProduct(4)).thenReturn(null);
		prodservice.getProduct(4);

	}

	@Test
	public void testGetAllProductsWithEmptyDb() {
		expectedEx.expect(PricingNotFoundException.class);
		expectedEx.expectMessage("There Are No Products");
		prodservice.getAllProducts();
	}

	@Test
	public void testAddProductService() {
		
	}
	//
	// @Test
	// public void addProductWithInvalidName() {
	// expectedEx.expect(PricingConflictsException.class);
	// addDummyProduct("$^kjdhfgksjdb89(*^&U&((", 400);
	// }

	// @Test
	// public void testAddProductWithPrice() {
	// ProductDto p = new ProductDto();
	// p.setProductName("hello");
	//
	// ProductPriceDto price = new ProductPriceDto();
	// price.setPrice(499.99);
	//
	// p.setPrice(price);
	//
	// ProductDto p1 = service.addProduct(p);
	// assertEquals(499.99, p.getPrice().getPrice(), 0);
	//
	// }
	//
	// @Test
	// public void addProductWithMoreThan25Chars() {
	// expectedEx.expect(PricingException.class);
	// addDummyProduct("sdjfhaskjdhfkjahsdfjhjksahdfkjhasjdfhkjsahdkfjhjsadfhkjsfkjsh",
	// 400);
	// }
	//
	// @Test
	// public void addProductWithLessThan2Chars() {
	// expectedEx.expect(PricingException.class);
	// addDummyProduct("s", 400);
	// }
	//
	// @Test
	// public void testGetNonExistingProduct() throws NotFoundException {
	// expectedEx.expect(NotFoundException.class);
	// service.getProduct(636363);
	// }
	//
	// @Test
	// public void removeProductWithInvalidId() throws NotFoundException {
	// expectedEx.expect(PricingException.class);
	// expectedEx.expectMessage("Product Id must be greater than 0");
	// service.removeProductById(-999);
	// }
	//
	// @Test
	// public void removeProductThatDoesntExist() throws NotFoundException {
	// expectedEx.expect(NotFoundException.class);
	// service.removeProductById(999999);
	// }
	//
	// @Test
	// public void testGetProductByName() throws NotFoundException {
	// addDummyProduct("dummy", 400);
	// ProductDto dto = service.getProductByName("dummy");
	// assertEquals("dummy", dto.getProductName());
	// assertTrue(dto.getProductId() > 0);
	// }
	//
	// @Test
	// public void testGetNonExistingProductByName() throws NotFoundException {
	// expectedEx.expect(NotFoundException.class);
	// service.getProductByName("NON EXISTING PRODUCT");
	// }
	//
	// @Test
	// public void testUpdateProductWithInvalidName() throws NotFoundException {
	// expectedEx.expect(PricingConflictsException.class);
	// ProductDto dto = addDummyProduct("Dummy", 400);
	// dto.setProductName("**03984%%##@@#$!!22342rsvd");
	// service.updateProduct(dto);
	// }
	//
	// @Test
	// public void testUpdateNonExistingProduct() throws NotFoundException {
	// expectedEx.expect(NotFoundException.class);
	// ProductDto dto = new ProductDto();
	// dto.setProductId(999);
	// dto.setProductName("NON EXISTING PRODUCT");
	// service.updateProduct(dto);
	// }
	//
	// @Test
	// public void testDeleteProductWithServices() throws NotFoundException {
	// expectedEx.expect(NotFoundException.class);
	// ProductDto p = addDummyProduct("dummy product", 400);
	// ServiceDto s = addDummyService("dummy service");
	// logger.info(p);
	// ProductServiceDto ps = addDummyProdService(p.getProductId(),
	// s.getServiceId());
	// service.removeProductById(p.getProductId());
	// assertNull(service.getProductService(ps.getPsId()));
	// assertNull(service.getProduct(p.getProductId()));
	//
	// }

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
		return null;
	}

	// public ProductServiceDto addDummyProdService(int productId, int serviceId) {
	// ProductServiceDto ps = new ProductServiceDto();
	// ps.setProductId(productId);
	// // ps.setServiceId(serviceId);
	// ps.setFreeUnits(100);
	// ps.setUnitType("mb");
	// ps.setServicePrice(0.5);
	// return service.addProductService(ps);
	// }
	//
	// public ProductDto addDummyProduct(String name, double price) {
	// ProductDto p = new ProductDto();
	// ProductPriceDto pr = new ProductPriceDto();
	// pr.setPrice(price);
	// p.setProductName(name);
	// p.setPrice(pr);
	//
	// return service.addProduct(p);
	// }

}
