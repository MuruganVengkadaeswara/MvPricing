package com.onebill.pricing.services;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.onebill.pricing.dao.ServiceDao;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.entities.Service;

@org.springframework.stereotype.Service
public class ServiceManagerServiceImpl implements ServiceManagerService {

	@Autowired
	private ServiceDao servicedao;

	@Autowired
	private ModelMapper mapper;

	Logger logger = Logger.getLogger(ServiceManagerServiceImpl.class);

	@Override
	public ServiceDto addService(ServiceDto dto) {
		Service service = mapper.map(dto, Service.class);
		servicedao.addService(service);
		if (service != null) {
			logger.info("Service added" + service);
			return mapper.map(service, ServiceDto.class);
		} else {
			return null;
		}
	}

	@Override
	public ServiceDto removeService(int serviceId) {
		Service service = servicedao.removeService(serviceId);
		if (service != null) {
			logger.info("Service deleted" + service);
			return mapper.map(service, ServiceDto.class);
		} else {
			return null;
		}

	}

	@Override
	public ServiceDto updateService(ServiceDto dto) {
		Service service = mapper.map(dto, Service.class);
		service = servicedao.updateService(service);
		if (service != null) {
			logger.info("service updated" + service);
			return mapper.map(service, ServiceDto.class);
		} else {
			return null;
		}
	}

	@Override
	public ServiceDto getService(int serviceId) {
		Service service = servicedao.getService(serviceId);
		if (service != null) {
			logger.info("service returned" + service);
			return mapper.map(service, ServiceDto.class);
		} else {
			return null;
		}
	}

	@Override
	public List<ServiceDto> getAllServices() {
		List<Service> list = servicedao.getAllServices();
		List<ServiceDto> dtolist = new ArrayList<ServiceDto>();
		if (!list.isEmpty()) {
			for (Service s : list) {
				dtolist.add(mapper.map(s, ServiceDto.class));
			}
		}
		return dtolist;
	}

}
