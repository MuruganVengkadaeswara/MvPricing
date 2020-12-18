package com.onebill.pricing.daotest;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.onebill.pricing.PricingAppConfiguration;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PricingAppConfiguration.class)
@WebAppConfiguration
public class TestExtraPriceDao {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
