package com.onebill.pricing.daotest;

import static org.junit.Assert.assertEquals;

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
import com.onebill.pricing.entities.Plan;
import com.sun.istack.logging.Logger;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PricingAppConfiguration.class)
@WebAppConfiguration
public class TestPlanDao {

	@Autowired
	PlanDao dao;

	@Autowired
	ApplicationContext context;
	
	
	Logger logger = Logger.getLogger(TestPlanDao.class);
	
	
	@Test
	public void testAddPlan() {
		Plan plan = new Plan();
		plan.setProductId(1);
		plan.setValidityDays(30);
		plan.setExtrasPaid("YES");
		Plan p = dao.addPlan(plan);
		logger.info(p.toString());
		assertEquals(1, p.getProductId());
		assertEquals(30, p.getValidityDays());
		assertEquals("YES", p.getExtrasPaid());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
