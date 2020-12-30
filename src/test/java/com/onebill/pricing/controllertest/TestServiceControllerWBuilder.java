package com.onebill.pricing.controllertest;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.onebill.pricing.controllers.TestController;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.entities.Service;
import com.onebill.pricing.servicebuilders.ServiceBuilder;
import com.onebill.pricing.services.ServiceManagerService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(ServiceBuilder.class)
public class TestServiceControllerWBuilder {

	@InjectMocks
	TestController controller;

	@Mock
	ServiceManagerService service;

	Logger logger = Logger.getLogger(TestServiceControllerWBuilder.class);

	@Test
	public void testGetServices() {

		// PowerMockito.mockStatic(ServiceBuilder.class);
		Mockito.mockStatic(ServiceBuilder.class);
		List<ServiceDto> list = new ArrayList<ServiceDto>();

		ServiceDto dto = new ServiceDto();
		dto.setServiceId(1);
		dto.setServiceName("dummy");

		list.add(dto);
		
		List<Service> slist = new ArrayList<>();
		
		Service s = new Service();
		s.setServiceId(1);
		s.setServiceName("dummy");
		
		slist.add(s);
		
		Mockito.when(ServiceBuilder.getAllServices()).thenReturn(list);
		Mockito.when(service.getAllServices()).thenReturn(list);

		logger.info(ServiceBuilder.getAllServices());
		// logger.info(ServiceBuilder.getServices());
		// logger.info(ServiceBuilder.getAllServices());
		logger.info(controller.getAllServices());
	}

	@Test
	public void testGetString() {
		Mockito.mockStatic(ServiceBuilder.class);
		Mockito.when(ServiceBuilder.returnString()).thenReturn("Hello");
		logger.info(ServiceBuilder.returnString());

	}

}
