package com.onebill.pricing.servicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.exceptions.PricingConflictsException;
import com.onebill.pricing.exceptions.PricingException;
import com.onebill.pricing.services.ServiceManagerService;
import com.sun.istack.logging.Logger;

import javassist.NotFoundException;

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

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void testAddDuplicateService() {
		expectedEx.expect(PricingConflictsException.class);
		addDummyService("dummy");
		addDummyService("dummy");
	}

	@Test
	public void testAddInvalidServiceName() {
		expectedEx.expect(PricingConflictsException.class);
		addDummyService("329((-2902-2-2-9234sdfb");
	}

	@Test
	public void testAddServiceMorethan25Chars() {
		expectedEx.expect(PricingConflictsException.class);
		addDummyService("Airtel mosdfsjhdfksjdhhdfskjshdfkjhskdhfskhdfhsjk");
	}

	@Test
	public void testUpdateServiceWithInvalidName() {
		expectedEx.expect(PricingException.class);
		ServiceDto serv = addDummyService("dummy");
		serv.setServiceName("DKsioi04-924-492???pp[p34423");
		service.updateService(serv);
	}

	@Test
	public void testAddService() {
		ServiceDto serv = addDummyService("dummy");
		logger.info(serv.toString());
		assertTrue(serv.getServiceId() > 0);
		assertEquals("dummy", serv.getServiceName());
	}

	@Test
	public void testRemoveService() throws NotFoundException {
		ServiceDto serv = addDummyService("dummy");
		serv = service.removeService(serv.getServiceId());
		assertEquals("dummy", serv.getServiceName());
	}

	@Test
	public void testGetServiceWithName() throws NotFoundException {
		addDummyService("dummy");
		ServiceDto serv = service.getServiceByName("dummy");
		assertTrue(serv.getServiceId() > 0);
		assertEquals("dummy", serv.getServiceName());
	}

	@Test
	public void testRemoveServiceWithInvalidId() throws NotFoundException {
		expectedEx.expect(PricingException.class);
		service.getService(-9282);
	}

	@Test
	public void testGetNonExistingService() throws NotFoundException {
		expectedEx.expect(NotFoundException.class);
		service.getService(9298233);
	}

	@Test
	public void testGetNonExistingServiceByName() throws NotFoundException {
		expectedEx.expect(NotFoundException.class);
		service.getServiceByName("NON EXISTING SERVICE");
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
