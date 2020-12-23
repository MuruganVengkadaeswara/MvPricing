package com.onebill.pricing.servicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import com.onebill.pricing.dto.PlanDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ProductPriceDto;
import com.onebill.pricing.exceptions.PricingConflictsException;
import com.onebill.pricing.exceptions.PricingException;
import com.onebill.pricing.services.PlanManagerService;
import com.onebill.pricing.services.ProductManagerService;
import com.onebill.pricing.testconfig.PricingAppTestConfiguration;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PricingAppConfiguration.class)
@WebAppConfiguration
public class TestPlanManagerservice {

	@Autowired
	ApplicationContext context;

	@Autowired
	PlanManagerService service;

	@Autowired
	ProductManagerService prodService;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void addProductToPlanWithoutProductPrice() {
		expectedEx.expect(PricingConflictsException.class);
		ProductDto dto = addDummyProduct("dummy");
		addDummyPlan(dto.getProductId(), "monthly");

	}

	@Test
	public void testaddPlanWithoutProductId() {
		expectedEx.expect(PricingException.class);
		expectedEx.expectMessage("Product id must be > 0");
		PlanDto plan = new PlanDto();
		plan.setPlanType("Monthly");
		service.addPlan(plan);
	}

	@Test
	public void testAddPlanWithNonExistingProductId() {
		expectedEx.expect(PricingConflictsException.class);
		addDummyPlan(9999, "monthly");
	}

	@Test
	public void testAddPlanWithInvalidPlanType() {
		expectedEx.expect(PricingConflictsException.class);
		ProductDto p = addDummyProduct("dummy");
		addDummyPlan(p.getProductId(), "((#(&eo[we]]");
	}

	@Test
	public void addPlanWithPrice() {
		expectedEx.expect(PricingConflictsException.class);
		ProductDto p = addDummyProduct("dummy");
		ProductPriceDto price = new ProductPriceDto();
		price.setPrice(400);
		price.setProductId(p.getProductId());
		prodService.addProductPrice(price);

		PlanDto plan = addDummyPlan(p.getProductId(), "monthly");

		assertTrue(plan.getPlanId() > 0);
		assertEquals(p.getProductId(), plan.getProductId());
	}

	@Test
	public void updatePlanWithInvalidPlanType() {

		expectedEx.expect(PricingConflictsException.class);

		ProductDto p = addDummyProduct("dummy");
		ProductPriceDto price = new ProductPriceDto();
		price.setPrice(400);
		price.setProductId(p.getProductId());
		prodService.addProductPrice(price);

		PlanDto plan = addDummyPlan(p.getProductId(), "monthly");
		plan.setPlanType("(9i4jsidjflksdjfl03i4j8riw");
		service.updatePlan(plan);

	}

	public ProductDto addDummyProduct(String name) {
		ProductDto p = new ProductDto();
		p.setProductName(name);
		return prodService.addProduct(p);
	}

	public PlanDto addDummyPlan(int productId, String planType) {
		PlanDto plan = new PlanDto();
		plan.setProductId(productId);
		plan.setPlanType(planType);
		return service.addPlan(plan);
	}

}
