package com.onebill.pricing.servicetest;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

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
import com.onebill.pricing.dto.BundleDto;
import com.onebill.pricing.dto.BundleProductDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ProductServiceDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.exceptions.PricingConflictsException;
import com.onebill.pricing.exceptions.PricingException;
import com.onebill.pricing.services.BundleManagerService;
import com.onebill.pricing.services.ProductManagerService;
import com.onebill.pricing.services.ServiceManagerService;

import javassist.NotFoundException;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PricingAppConfiguration.class)
@WebAppConfiguration
public class TestBundleManagerService {

	@Autowired
	ApplicationContext context;

	@Autowired
	ProductManagerService prodService;

	@Autowired
	BundleManagerService bundleService;

	@Autowired
	ServiceManagerService smService;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void testAddBundleWithInvalidName() {
		expectedEx.expect(PricingConflictsException.class);
		addDummyBundle("376HNKE2137(", "monthly");
	}

	@Test
	public void testAddBundleWithInvalidType() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("The bundle Type must either be monthly,yearly,weekly or daily");
		addDummyBundle("dummy bundle", "nvadzf");
	}

	@Test
	public void testUpdateBundleWithInvalidName() {
		expectedEx.expect(PricingConflictsException.class);
		BundleDto dto = addDummyBundle("dummy bundle", "monthly");
		dto.setBundleName("8**88**iehfhd023kudnfkjn");
		bundleService.updateBundle(dto);
	}

	@Test
	public void testGetBundleByName() throws NotFoundException {
		addDummyBundle("dummy bundle", "monthly");
		BundleDto dto = bundleService.getBundleByName("dummy bundle");
		assertEquals("dummy bundle", dto.getBundleName());
		assertTrue(dto.getBundleId() > 0);
		assertEquals("monthly", dto.getBundleType());
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
	public void testAddProductToBundleWithoutService() {
		expectedEx.expect(PricingConflictsException.class);

		expectedEx.expectMessage("The product to be added has no service please update the services");

		BundleDto bundle = addDummyBundle("dummy", "monthly");
		ProductDto product = addDummyProduct("dummy product");
		BundleProductDto bp = new BundleProductDto();
		bp.setBundleId(bundle.getBundleId());
		bp.setProductId(product.getProductId());

		bundleService.addBundleProduct(bp);
	}

	@Test
	public void testAddProductToBundlewithoutPrice() {

		expectedEx.expect(PricingException.class);

		expectedEx.expectMessage("The product to be added has no Price ! please update the price");

		BundleDto bundle = addDummyBundle("dummy", "monthly");
		ProductDto product = addDummyProduct("dummy product");
		addDummyProductService(product.getProductId());
		BundleProductDto bp = new BundleProductDto();
		bp.setBundleId(bundle.getBundleId());
		bp.setProductId(product.getProductId());

		bundleService.addBundleProduct(bp);

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
