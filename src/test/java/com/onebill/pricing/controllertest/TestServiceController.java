package com.onebill.pricing.controllertest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.onebill.pricing.controllers.ServiceController;
import com.onebill.pricing.dto.ResponseDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.services.ServiceManagerService;

import javassist.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class TestServiceController {

	@Mock
	ServiceManagerService service;

	@InjectMocks
	ServiceController controller = new ServiceController();

	@Test
	public void testGetAllServices() throws NotFoundException {

		List<ServiceDto> list = new ArrayList<ServiceDto>();
		ServiceDto dto = new ServiceDto();
		dto.setServiceName("Hello");
		dto.setServiceId(1);
		list.add(dto);
		Mockito.when(service.getAllServices()).thenReturn(list);
		assertTrue(controller.getAllServices() != null);
		ResponseDto res = controller.getAllServices();
		assertEquals(list, res.getResponse());

	}
	
	@Test
	public void testGetServiceById() throws NotFoundException {
		Mockito.when(service.getService(1)).thenReturn(new ServiceDto());
		assertNotNull(controller.getService(1));
	}

}
