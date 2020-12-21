package com.onebill.pricing.servicetest;

import static org.junit.Assert.*;

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
import com.onebill.pricing.dto.PlanDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.services.PlanManagerService;
import com.onebill.pricing.services.ProductManagerService;

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

	@Test
	public void testAddPlan() {

		ProductDto p = addDummyProduct("dummy product");
		PlanDto plan = addDummyPlan(p.getProductId(), 30);

		assertTrue(plan.getPlanId() > 0);
		assertEquals(30, plan.getValidityDays());
		assertEquals(p.getProductId(), plan.getProductId());

	}

	@Test(expected = PersistenceException.class)
	public void testAddDuplicatePlansWithSameProductId() {
		ProductDto p = addDummyProduct("dummy product");
		addDummyPlan(p.getProductId(), 30);
		addDummyPlan(p.getProductId(), 45);

	}

	@Test
	public void testGetPlan() {
		ProductDto p = addDummyProduct("dummy product");
		PlanDto plan = addDummyPlan(p.getProductId(), 30);

		plan = service.getPlan(plan.getPlanId());

		assertNotNull(plan);
		assertEquals(30, plan.getValidityDays());
		assertEquals(p.getProductId(), plan.getProductId());
	}

	@Test
	public void testDeletePlan() {

		ProductDto p = addDummyProduct("dummy product");
		PlanDto plan = addDummyPlan(p.getProductId(), 30);

		plan = service.deletePlan(plan.getPlanId());

		assertNull(service.getPlan(plan.getPlanId()));

	}

	@Test
	public void testUpdatePlan() {
		ProductDto p = addDummyProduct("dummy product");
		PlanDto plan = addDummyPlan(p.getProductId(), 30);
		plan.setValidityDays(45);

		plan = service.updatePlan(plan);

		assertEquals(45, plan.getValidityDays());
	}

	public ProductDto addDummyProduct(String name) {
		ProductDto p = new ProductDto();
		p.setProductName(name);
		return prodService.addProduct(p);
	}

	public PlanDto addDummyPlan(int productId, int validityDays) {
		PlanDto plan = new PlanDto();
		plan.setProductId(productId);
		plan.setValidityDays(validityDays);
		return service.addPlan(plan);
	}

}
