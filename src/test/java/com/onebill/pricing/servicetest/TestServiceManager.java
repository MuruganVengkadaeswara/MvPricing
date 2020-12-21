package com.onebill.pricing.servicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.services.ServiceManagerService;
import com.sun.istack.logging.Logger;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PricingAppConfiguration.class)
@WebAppConfiguration
public class TestServiceManager {

	@Autowired
	ServiceManagerService service;

	@Autowired
	ApplicationContext context;

	Logger logger = Logger.getLogger(TestServiceManager.class);

	@Test
	public void testAddService() {
		ServiceDto serv = addDummyService("dummy");
		logger.info(serv.toString());
		assertTrue(serv.getServiceId() > 0);
		assertEquals("dummy", serv.getServiceName());
	}

	@Test
	public void testRemoveService() {
		ServiceDto serv = addDummyService("dummy");
		serv = service.removeService(serv.getServiceId());
		assertEquals("dummy", serv.getServiceName());
	}

	@Test
	public void testUpdateService() {
		ServiceDto serv = addDummyService("dummy");
		serv.setServiceName("update dummy");
		ServiceDto serv1 = service.updateService(serv);
		assertEquals(serv, serv1);
	}

	@Test
	public void getAllServices() {
		addDummyService("dummy");
		List<ServiceDto> list = service.getAllServices();
		assertTrue(!list.isEmpty());
	}

	public ServiceDto addDummyService(String name) {
		ServiceDto s = new ServiceDto();
		s.setServiceName(name);
		return service.addService(s);

	}

}
