package com.onebill.pricing.servicetest;

import static org.junit.Assert.*;

import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import com.onebill.pricing.dao.ServiceDao;
import com.onebill.pricing.dao.ServiceDaoImpl;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.entities.Service;
import com.onebill.pricing.services.ServiceManagerService;
import com.onebill.pricing.services.ServiceManagerServiceImpl;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

public class TestServiceManager {

	@Rule
	public EasyMockRule rule = new EasyMockRule(this);

	@Mock
	private ServiceDao servDao;
	

	@TestSubject
	private ServiceManagerService service = new ServiceManagerServiceImpl();

	@Test
	public void addService() {
		ServiceDto serv = new ServiceDto();
		serv.setServiceName("dummy");

		Service s = new Service();
		s.setServiceName("dummy");
		s.setServiceId(1);

		expect(servDao.addService(s)).andReturn(s);
		assertNotNull(service.addService(serv));

	}

}
