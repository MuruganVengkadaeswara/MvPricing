package com.onebill.pricing.servicetest;

import java.util.ArrayList;
import java.util.List;

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
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.Mockito.*;

import com.onebill.pricing.dao.AdditionalPriceDao;
import com.onebill.pricing.dao.BundleProductDao;
import com.onebill.pricing.dao.ProductDao;
import com.onebill.pricing.dao.ProductPriceDao;
import com.onebill.pricing.dao.ProductServiceDao;
import com.onebill.pricing.dao.ServiceDao;
import com.onebill.pricing.dao.ServiceDaoImpl;
import com.onebill.pricing.dto.AdditionalPriceDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ProductPriceDto;
import com.onebill.pricing.dto.ProductServiceDto;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.entities.Service;
import com.onebill.pricing.exceptions.PricingConflictsException;
import com.onebill.pricing.exceptions.PricingException;
import com.onebill.pricing.exceptions.PricingNotFoundException;
import com.onebill.pricing.services.ProductManagerService;
import com.onebill.pricing.services.ProductManagerServiceImpl;

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

	@Mock
	ModelMapper mapper;
	//
	// @Before
	// public void init() {
	// MockitoAnnotations.initMocks(this);
	// }

	@Test
	public void testAddProductWithNullName() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Please Provide a product Name");
		ProductDto dto = new ProductDto();
		prodservice.addProduct(dto);
	}

	@Test
	public void addProductNameWithMoreThan25Chars() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Product Name must be within 25 characters");
		ProductDto dto = new ProductDto();
		dto.setProductName("ALFHOUGHWORNVJVNZKDNVLNDVlKNDVLKNDLVKNKLDNV");
		prodservice.addProduct(dto);
	}

	@Test
	public void testAddproductWithNullInput() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Product Cannot be null");
		ProductDto dto = null;
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
	public void testAddProduct() {
		ProductDto dto = new ProductDto();
		dto.setProductName("Airtel");

		ProductPriceDto price = new ProductPriceDto();
		price.setPrice(400);

		dto.setPrice(price);

		ProductServiceDto serv = new ProductServiceDto();
		serv.setFreeUnits(400);
		serv.setServiceId(1);
		serv.setUnitType("sms");
		serv.setServiceId(1);

		List<ProductServiceDto> list = new ArrayList<>();
		list.add(serv);

		List<AdditionalPriceDto> plist = new ArrayList<>();

		dto.setServices(list);
		dto.setAdditionalPrices(plist);

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

}
