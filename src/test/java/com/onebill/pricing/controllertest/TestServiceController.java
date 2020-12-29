package com.onebill.pricing.controllertest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.onebill.pricing.controllers.ServiceController;
import com.onebill.pricing.dto.ResponseDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.exceptions.PricingNotFoundException;
import com.onebill.pricing.services.ServiceManagerService;

import javassist.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class TestServiceController {

	private MockMvc mockmvc;
	
	@Mock
	ServiceManagerService service;

	@InjectMocks
	ServiceController controller = new ServiceController();

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

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

	@Test
	public void testGetNonExistingServiceById() throws NotFoundException {
		expectedEx.expect(PricingNotFoundException.class);
		Mockito.when(service.getService(1)).thenReturn(null);
		assertNotNull(controller.getService(1));
	}

	@Test
	public void testGetServiceByName() throws NotFoundException {
		Mockito.when(service.getServiceByName("Hello")).thenReturn(new ServiceDto());
		assertNotNull(controller.getService("Hello"));
	}

	@Test
	public void testNonExistingGetServiceByName() {
		expectedEx.expect(PricingNotFoundException.class);
		Mockito.when(service.getServiceByName("Hello")).thenReturn(null);
		assertNotNull(controller.getService("Hello"));
	}

	@Test
	public void testAddService() {
		Mockito.when(service.addService(Mockito.any())).thenReturn(new ServiceDto());
		ServiceDto dto = new ServiceDto();
		ResponseDto res = controller.addService(dto);
		assertNotNull(res);
	}

}
