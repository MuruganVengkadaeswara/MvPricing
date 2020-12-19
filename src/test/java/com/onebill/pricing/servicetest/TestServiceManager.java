package com.onebill.pricing.servicetest;

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
		ServiceDto serv = new ServiceDto();
		serv.setServiceName("Messaging");
		logger.info(serv.toString());
		assertNotNull(service.addService(serv));
	}
	
	@Test
	public void testRemoveService() {
		ServiceDto serv = service.removeService(2);
		assertNotNull(serv);
	}
	
	@Test
	public void testUpdateService() {
		ServiceDto serv = new ServiceDto();
		serv.setServiceId(1);
		serv.setServiceName("Jio Messaging");
		ServiceDto serv1 = service.updateService(serv);
		assertEquals(serv, serv1);
	}
	
	@Test
	public void testDeleteService() {
		assertNotNull(service.removeService(1));
	}
	
	@Test
	public void getAllServices() {
		assertNotNull(service.getAllServices());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
