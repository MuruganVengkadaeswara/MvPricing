package com.onebill.pricing.servicetest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnit44Runner;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.onebill.pricing.PricingAppConfiguration;
import com.onebill.pricing.dao.BundleDao;
import com.onebill.pricing.dao.BundleProductDao;
import com.onebill.pricing.dao.ProductDao;
import com.onebill.pricing.dao.ProductServiceDao;
import com.onebill.pricing.dto.BundleDto;
import com.onebill.pricing.dto.BundleProductDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ProductServiceDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.entities.Bundle;
import com.onebill.pricing.entities.BundleProduct;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.exceptions.PricingConflictsException;
import com.onebill.pricing.exceptions.PricingException;
import com.onebill.pricing.services.BundleManagerService;
import com.onebill.pricing.services.BundleManagerServiceImpl;
import com.onebill.pricing.services.ProductManagerService;
import com.onebill.pricing.services.ProductManagerServiceImpl;
import com.onebill.pricing.services.ServiceManagerService;
import com.onebill.pricing.services.ServiceManagerServiceImpl;

import javassist.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class TestBundleManagerService {

	@Mock
	BundleDao bundleDao;

	@Mock
	BundleProductDao bundleProdDao;

	@Mock
	ProductServiceDao prodServDao;

	@Mock
	ProductDao prodDao;

	@InjectMocks
	ProductManagerService prodService = new ProductManagerServiceImpl();

	@InjectMocks
	BundleManagerService bundleService = new BundleManagerServiceImpl();

	@InjectMocks
	ServiceManagerService smService = new ServiceManagerServiceImpl();

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void testAddBundleWithNullName() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Bundle Name Cannot Be null");
		BundleDto dto = new BundleDto();
		bundleService.addBundle(dto);
	}

	@Test
	public void testAddBundleWithNoProducts() {
		expectedEx.expect(PricingConflictsException.class);
		BundleDto dto = new BundleDto();
		dto.setBundleName("Diwali Bundle");
		bundleService.addBundle(dto);
	}

	@Test
	public void testAddBundleWithInvalidName() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Bundle Products Cannot be null");
		BundleDto dto = new BundleDto();
		dto.setBundleName("AJSFHAJ!##");
		bundleService.addBundle(dto);
	}

	@Test
	public void testAddBundleWithNonExistingProduct() {

		expectedEx.expect(PricingConflictsException.class);
		List<BundleProductDto> bp = new ArrayList<BundleProductDto>();

		BundleProductDto bpDto1 = new BundleProductDto();
		bpDto1.setBundleId(1);
		bpDto1.setProductId(2);

		bp.add(bpDto1);

		BundleDto dto = new BundleDto();
		dto.setBundleName("Dummy");
		dto.setBundleProducts(bp);

		bundleService.addBundle(dto);
	}

	@Test
	public void testAddBundleWithDuplicateProducts() {

		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Trying to add Duplicate Products , please Remove duplicates");
		List<BundleProductDto> bp = new ArrayList<BundleProductDto>();

		BundleProductDto bpDto1 = new BundleProductDto();
		bpDto1.setBundleId(1);
		bpDto1.setProductId(2);

		bp.add(bpDto1);
		bp.add(bpDto1);

		BundleDto dto = new BundleDto();
		dto.setBundleName("Dummy");
		dto.setBundleProducts(bp);

		Mockito.when(prodDao.getProduct(2)).thenReturn(new Product());

		bundleService.addBundle(dto);
	}

	@Test
	public void testAddBundleWithExistingName() {

		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Bundle with name Dummy Already exists");
		List<BundleProductDto> bp = new ArrayList<BundleProductDto>();

		BundleProductDto bpDto1 = new BundleProductDto();
		bpDto1.setBundleId(1);
		bpDto1.setProductId(2);

		bp.add(bpDto1);

		BundleDto dto = new BundleDto();
		dto.setBundleName("Dummy");
		dto.setBundleProducts(bp);

		Mockito.when(prodDao.getProduct(2)).thenReturn(new Product());
		Mockito.when(bundleDao.getBundleByName("Dummy")).thenReturn(new Bundle());

		bundleService.addBundle(dto);
	}

	@Test
	public void testAddBundleWithInvalidType() {

		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("The bundle Type must either be monthly,yearly,weekly or daily");
		List<BundleProductDto> bp = new ArrayList<BundleProductDto>();

		BundleProductDto bpDto1 = new BundleProductDto();
		bpDto1.setBundleId(1);
		bpDto1.setProductId(2);

		bp.add(bpDto1);

		BundleDto dto = new BundleDto();
		dto.setBundleName("Dummy");
		dto.setBundleProducts(bp);
		dto.setBundleType("dsjkhfkjdfh");

		Mockito.when(prodDao.getProduct(2)).thenReturn(new Product());

		bundleService.addBundle(dto);
	}

	@Test
	public void testUpdateBundleWithInvalidName() {
		expectedEx.expect(PricingConflictsException.class);
		BundleDto dto = addDummyBundle("dummy bundle", "monthly");
		dto.setBundleName("8**88**iehfhd023kudnfkjn");
		bundleService.updateBundle(dto);
	}

	@Test
	public void testGetNonExistingBundleByName() throws NotFoundException {
		expectedEx.expect(NotFoundException.class);
		bundleService.getBundleByName("THIS IS A NON EXISTING BUNDLE NAME");
	}

	@Test
	public void testGetBundleWithInvalidId() throws NotFoundException {
		expectedEx.expect(PricingException.class);
		bundleService.getBundle(-999);
	}

	@Test
	public void testDeleteBundleWithInvalidId() throws NotFoundException {
		expectedEx.expect(PricingException.class);
		bundleService.removeBundel(-929);
	}

	@Test
	public void testGetNonExistingBundleById() throws NotFoundException {
		expectedEx.expect(NotFoundException.class);
		bundleService.getBundle(99999);
	}


	@Test
	public void updateBundleWithInvalidName() {
		expectedEx.expect(PricingConflictsException.class);
		BundleDto bundle = addDummyBundle("dummy", "monthly");
		bundle.setBundleName("&&(&*UHFSNLDFSN*(WOI R");
		bundleService.updateBundle(bundle);
	}

	@Test
	public void updateBundleWithInvalidType() {
		expectedEx.expect(PricingConflictsException.class);
		BundleDto bundle = addDummyBundle("dummy", "monthly");
		bundle.setBundleType("jdjfdh09u4");
		bundleService.updateBundle(bundle);

	}

	public BundleDto addDummyBundle(String name, String bundleType) {
		BundleDto b = new BundleDto();
		b.setBundleName(name);
		b.setBundleType(bundleType);
		return bundleService.addBundle(b);
	}

	public ProductDto addDummyProduct(String name) {
		ProductDto p = new ProductDto();
		p.setProductName(name);
		return prodService.addProduct(p);
	}

	public ProductServiceDto addDummyProductService(int productId) {
		ServiceDto s = new ServiceDto();
		s.setServiceName("dummy");
		s = smService.addService(s);
		ProductServiceDto ps = new ProductServiceDto();
		ps.setProductId(productId);
//		ps.setServiceId(s.getServiceId());
		ps.setFreeUnits(100);
		ps.setUnitType("mb");
		ps.setServicePrice(49.99);
		return prodService.addProductService(ps);
	}

	public BundleProductDto addDummyBundleProduct(int bundleId, int productId) {
		BundleProductDto b = new BundleProductDto();
		b.setBundleId(bundleId);
		b.setProductId(productId);
		return bundleService.addBundleProduct(b);
	}

}
