package com.onebill.pricing.daotest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.transaction.Transactional;

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

	@Test
	public void testAddservice() {
		Service service = new Service();
		service.setServiceName("Dummy Service");
		Service s = dao.addService(service);
		assertEquals("Dummy Service", s.getServiceName());
	}

	@Test
	public void testRemoveService() {
		Service service = dao.removeService(2);
		assertEquals(2, service.getServiceId());
	}

	@Test
	public void testUpdateService() {
		Service s = new Service();
		s.setServiceId(1);
		s.setServiceName("Updated Service Name");
		assertEquals("Updated Service Name", dao.updateService(s).getServiceName());
	}

	@Test
	public void testGetService() {
		Service s = dao.getService(1);
		assertEquals(1, s.getServiceId());
	}

	@Test
	public void testGetAllServices() {
		List<Service> list = dao.getAllServices();
		assertNotNull(list);
	}

}
