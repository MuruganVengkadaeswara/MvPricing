package com.onebill.pricing.daotest;

import static org.junit.Assert.*;

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
import com.onebill.pricing.dao.BundleDao;
import com.onebill.pricing.entities.Bundle;
import com.sun.istack.logging.Logger;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PricingAppConfiguration.class)
@WebAppConfiguration
public class TestBundleDao {

	@Autowired
	BundleDao dao;

	@Autowired
	ApplicationContext context;

	Logger logger = Logger.getLogger(TestBundleDao.class);

	@Test
	public void testAddBundle() {
		Bundle b = addDummyBundle("Dummy", 500);
		assertTrue(b.getBundleId() > 0);
		assertEquals("Dummy", b.getBundleName());
		assertEquals(500, b.getValidityDays());

	}

	@Test(expected = PersistenceException.class)
	public void testAddDuplicateBundle() {
		addDummyBundle("Dummy", 500);
		addDummyBundle("Dummy", 500);
	}

	@Test
	public void testUpdateBundle() {
		Bundle b = addDummyBundle("Dummy", 400);
		b.setBundleName("Update Dummy");
		b.setValidityDays(450);

		Bundle b1 = dao.updateBundle(b);

		assertEquals(b.getBundleId(), b1.getBundleId());
		assertEquals("Update Dummy", b1.getBundleName());
		assertEquals(450, b1.getValidityDays());

	}

	@Test
	public void testGetBundleById() {

		Bundle b = addDummyBundle("Dummy", 200);

		Bundle b1 = dao.getBundle(b.getBundleId());

		assertEquals(b.getBundleId(), b1.getBundleId());
		assertEquals("Dummy", b1.getBundleName());
		assertEquals(200, b1.getValidityDays());

	}

	@Test
	public void testGetAllBundles() {

		addDummyBundle("dummy 1", 100);
		addDummyBundle("dummy 2", 200);
		addDummyBundle("dummy 3", 300);

		List<Bundle> bundles = dao.getAllBundles();

		assertEquals("dummy 3", bundles.get(bundles.size()-1).getBundleName());
		assertEquals(300, bundles.get(bundles.size()-1).getValidityDays());
		assertTrue(!bundles.isEmpty());

	}

	public Bundle addDummyBundle(String name, int days) {
		Bundle bundle = new Bundle();
		bundle.setBundleName(name);
		bundle.setValidityDays(days);
		return dao.addBundle(bundle);
	}

}
