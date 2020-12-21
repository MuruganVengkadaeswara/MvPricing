package com.onebill.pricing.daotest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.onebill.pricing.PricingAppConfiguration;
import com.onebill.pricing.dao.ServiceDao;
import com.onebill.pricing.entities.Service;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PricingAppConfiguration.class)
@WebAppConfiguration
public class TestServiceDao {

	@Autowired
	ServiceDao dao;

	@Autowired
	ApplicationContext context;

	Logger logger = Logger.getLogger(TestServiceDao.class);

	@Test
	public void testAddservice() throws Exception {
		Service service = addDummyService("Dummy");
		logger.info("added service " + service);
		assertEquals("Dummy", service.getServiceName());
	}

	@Test(expected = PersistenceException.class)
	public void testAddDuplicateService() {
		addDummyService("dummy");
		addDummyService("dummy");
	}

	@Test
	public void testUpdateService() throws Exception {

		Service s = addDummyService("dummy name");

		s.setServiceName("updated service name");
		Service s1 = dao.updateService(s);

		assertEquals("updated service name", s1.getServiceName());
	}

	@Test
	public void testGetService() throws Exception {
		Service s = addDummyService("dummy");

		Service s1 = dao.getService(s.getServiceId());
		assertEquals(s, s1);
	}

	@Test
	public void testGetAllServices() throws Exception {

		addDummyService("dummy");
		addDummyService("dummy 1");
		List<Service> list = dao.getAllServices();
		assertTrue(!list.isEmpty());

	}

	@Test
	public void testRemoveService() throws Exception {

		Service s = addDummyService("dummy");
		Service s1 = dao.removeService(s.getServiceId());
		assertEquals(s, s1);
	}

	public Service addDummyService(String name) {
		Service s = new Service();
		s.setServiceName(name);
		return dao.addService(s);
	}

}
