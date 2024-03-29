package com.onebill.pricing.servicetest;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

import com.onebill.pricing.dao.ProductServiceDao;
import com.onebill.pricing.dao.ProductServiceDaoImpl;
import com.onebill.pricing.dao.ServiceDao;
import com.onebill.pricing.dao.ServiceDaoImpl;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.entities.ProductService;
import com.onebill.pricing.entities.Service;
import com.onebill.pricing.exceptions.PricingConflictsException;
import com.onebill.pricing.exceptions.PricingException;
import com.onebill.pricing.exceptions.PricingNotFoundException;
import com.onebill.pricing.services.ServiceManagerService;
import com.onebill.pricing.services.ServiceManagerServiceImpl;

import javassist.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class TestServiceManagerService {

	@InjectMocks
	ServiceManagerServiceImpl service;

	@Mock
	ServiceDao servicedao;

	@Mock
	ProductServiceDao prodServDao;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	Logger logger = Logger.getLogger(TestServiceManagerService.class);

	Service serv;

	ServiceDto servDto;

	@Mock
	ModelMapper mapper;

	@Test
	public void testAddServiceWithNullInput() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Service Cannot be null");
		ServiceDto dto = null;
		service.addService(dto);
	}

	@Before
	public void setup() {
		servDto = new ServiceDto();
		servDto.setServiceId(2);
		servDto.setServiceName("dummy");

		serv = new Service();
		serv.setServiceId(2);
		serv.setServiceName("dummy");
	}

	@Test
	public void testGetService() throws NotFoundException {
		Service s = new Service();
		s.setServiceId(1);
		s.setServiceName("dummy");

		ServiceDto dto = new ServiceDto();
		dto.setServiceId(1);
		dto.setServiceName("dummy");

		Mockito.when(servicedao.getService(Mockito.anyInt())).thenReturn(s);
		Mockito.when(service.getService(1)).thenReturn(dto);
		assertNotNull(service.getService(1));
	}

	@Test
	public void testAddServiceWithNullServiceName() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Please Provide a service Name");
		ServiceDto dto = new ServiceDto();
		service.addService(dto);
	}

	@Test
	public void testAddServiceWithLessThan2Chars() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("The service Name must be within 2 and 25 characters");
		ServiceDto dto = new ServiceDto();
		dto.setServiceName("e");
		service.addService(dto);
	}

	@Test
	public void testAddDuplicateServiceName() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("The service with name Hello already exists");
		ServiceDto dto = new ServiceDto();
		dto.setServiceName("Hello");
		Mockito.when(servicedao.getServiceByName("Hello")).thenReturn(new Service());
		service.addService(dto);
	}

	@Test
	public void testRemoveServiceInUse() throws NotFoundException {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("The service is used By one or more products ! please remove them before deleting");
		Integer id = 2;
		List<ProductService> list = new ArrayList<ProductService>();
		ProductService p = new ProductService();
		list.add(p);
		Mockito.when(prodServDao.getAllProductServicesByServiceId(2)).thenReturn(list);
		service.removeService(id);
	}

	@Test
	public void testRemoveServiceWithNegativeId() throws NotFoundException {
		expectedEx.expect(PricingException.class);
		expectedEx.expectMessage("Service Id must be greater than 0");
		service.removeService(-2);
	}

	@Test
	public void testUpdateServiceWithNegativeId() {
		expectedEx.expect(PricingException.class);
		expectedEx.expectMessage(
				"the service id must be greater than 0 and name must contain only spaces and numbers and be within 25 characters");
		ServiceDto dto = new ServiceDto();
		dto.setServiceId(-5);
		dto.setServiceName("Hello");
		service.updateService(dto);
	}

	@Test
	public void testUpdateServiceWithInvalidName() {
		expectedEx.expect(PricingException.class);
		expectedEx.expectMessage(
				"the service id must be greater than 0 and name must contain only spaces and numbers and be within 25 characters");
		servDto = new ServiceDto();
		servDto.setServiceId(5);
		servDto.setServiceName("MDUY**63974((*(Y^#");
		service.updateService(servDto);

	}

	@Test
	public void testGetServiceWithNegativeId() throws NotFoundException {
		expectedEx.expect(PricingException.class);
		expectedEx.expectMessage("The service id must be greater than 0");
		service.getService(-4);
	}

	@Test
	public void testGetAllServicesWithEmptyDb() {
		expectedEx.expect(PricingNotFoundException.class);
		expectedEx.expectMessage("There are no services");
		List<Service> list = new ArrayList<>();
		Mockito.when(servicedao.getAllServices()).thenReturn(list);
		service.getAllServices();
	}

	@Test
	public void testGetAllProductswithNegativeServiceId() {
		expectedEx.expect(PricingException.class);
		expectedEx.expectMessage("service Id must be greater than 0");
		service.getAllProductsOfService(-6);
	}

	@Test
	public void testGetNonExistingServiceByName() {
		expectedEx.expect(PricingNotFoundException.class);
		expectedEx.expectMessage("Service With Name Hello doesn't exist");
		Mockito.when(servicedao.getServiceByName("Hello")).thenReturn(null);
		service.getServiceByName("Hello");
	}

	@Test
	public void testAddServiceWithMoreThan25Characters() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("The service Name must be within 2 and 25 characters");
		ServiceDto serv = new ServiceDto();
		serv.setServiceName("Ajhsjahfdjhsdjhkshdkfhdslsasdasdgsg");
		service.addService(serv);

	}

	public Service makeService(String name, int id) {
		Service serv = new Service();
		serv.setServiceId(id);
		serv.setServiceName(name);
		return serv;
	}

}
