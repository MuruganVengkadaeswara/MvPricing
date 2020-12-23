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
import com.onebill.pricing.dao.PlanDao;
import com.onebill.pricing.dao.ProductDao;
import com.onebill.pricing.dao.ProductPriceDao;
import com.onebill.pricing.entities.Plan;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.entities.ProductPrice;
import com.onebill.pricing.exceptions.PricingException;
import com.sun.istack.logging.Logger;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PricingAppConfiguration.class)
@WebAppConfiguration
public class TestPlanDao {

	@Autowired
	PlanDao planDao;

	@Autowired
	ProductDao prodDao;

	@Autowired
	ApplicationContext context;

	@Autowired
	ProductPriceDao priceDao;

	Logger logger = Logger.getLogger(TestPlanDao.class);

	@Test
	public void testAddPlan() {

		Product p = addDummyProduct("dummy product");
		addDummyPrice(p.getProductId(), 100);

		Plan plan = addDummyPlan(p.getProductId(), 30);

		plan = planDao.addPlan(plan);
		logger.info(plan.toString());

		assertTrue(plan.getPlanId() > 0);
		assertEquals(plan.getProductId(), p.getProductId());

	}

	@Test(expected = PricingException.class)
	public void testAddPlanWithoutProduct() {

		Plan plan = new Plan();
		plan.setPlanType("monthly");
		planDao.addPlan(plan);
	}

	@Test
	public void testUpdatePlan() {

		Product p = addDummyProduct("dummy product");
		addDummyPrice(p.getProductId(), 100);

		Plan plan = addDummyPlan(p.getProductId(), 30);


		Plan p1 = planDao.updatePlan(plan);

		assertEquals(plan.getPlanId(), p1.getPlanId());
	}

	@Test
	public void testRemovePlan() {

		Product p = addDummyProduct("dummy product");
		addDummyPrice(p.getProductId(), 100);

		Plan plan = addDummyPlan(p.getProductId(), 30);

		plan = planDao.removePlanbyId(plan.getPlanId());

		assertEquals(plan.getProductId(), p.getProductId());

	}

	@Test
	public void testGetPlanById() {

		Product p = addDummyProduct("dummy product");
		addDummyPrice(p.getProductId(), 100);

		Plan plan = addDummyPlan(p.getProductId(), 30);

		plan = planDao.getPlanById(plan.getPlanId());

		assertEquals(plan.getProductId(), p.getProductId());
	}

	@Test
	public void testGetAllPlans() {

		Product p = addDummyProduct("dummy product");
		Product p1 = addDummyProduct("dummy product1");
		Product p2 = addDummyProduct("dummy product2");
		addDummyPrice(p.getProductId(), 100);
		addDummyPrice(p1.getProductId(), 100);
		addDummyPrice(p2.getProductId(), 100);

		addDummyPlan(p.getProductId(), 30);
		addDummyPlan(p1.getProductId(), 40);
		addDummyPlan(p2.getProductId(), 70);

		List<Plan> list = planDao.getAllPlans();

		assertTrue(!list.isEmpty());

	}

	@Test(expected = PersistenceException.class)
	public void addDuplicateProductToPlan() {

		Product p = addDummyProduct("dummy product");
		addDummyPrice(p.getProductId(), 100);

		addDummyPlan(p.getProductId(), 30);
		addDummyPlan(p.getProductId(), 40);

	}

	public Product addDummyProduct(String name) {
		Product product = new Product();
		product.setProductName(name);
		return prodDao.addProduct(product);
	}

	public ProductPrice addDummyPrice(int productId, double amt) {
		ProductPrice price = new ProductPrice();
		price.setProductId(productId);
		price.setPrice(amt);
		return priceDao.addProductPrice(price);
	}

	public Plan addDummyPlan(int productId, int days) {
		Plan p = new Plan();
		p.setProductId(productId);
		return planDao.addPlan(p);
	}

}
