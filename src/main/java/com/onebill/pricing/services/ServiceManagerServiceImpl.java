package com.onebill.pricing.services;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.onebill.pricing.dao.ProductServiceDao;
import com.onebill.pricing.dao.ServiceDao;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.entities.Service;
import com.onebill.pricing.exceptions.PricingConflictsException;
import com.onebill.pricing.exceptions.PricingException;

import javassist.NotFoundException;

@org.springframework.stereotype.Service
public class ServiceManagerServiceImpl implements ServiceManagerService {

	@Autowired
	private ServiceDao servicedao;

	@Autowired
	private ProductServiceDao prodServDao;

	@Autowired
	private ModelMapper mapper;

	Logger logger = Logger.getLogger(ServiceManagerServiceImpl.class);

	@Override
	public ServiceDto addService(ServiceDto dto) {

		if (dto.getServiceName() != null) {
			if (servicedao.getServiceByName(dto.getServiceName()) == null) {
				if (dto.getServiceName().matches("[A-Za-z0-9 ]{2,25}")) {
					Service service = mapper.map(dto, Service.class);
					servicedao.addService(service);
					if (service != null) {
						logger.info("Service added" + service);
						return mapper.map(service, ServiceDto.class);
					} else {
						return null;
					}
				} else {
					throw new PricingConflictsException(
							"The service Name Must contain be only numbers,letters and spaces and must be within 2 and 25 characters");
				}
			} else {
				throw new PricingConflictsException(
						"The service with name " + dto.getServiceName() + " already exists");
			}
		} else {
			throw new PricingConflictsException("Please Provide a service Name");
		}

	}

	@Override
	public ServiceDto removeService(int serviceId) throws NotFoundException {
		if (prodServDao.getAllProductServicesByServiceId(serviceId).isEmpty()) {
			if (serviceId > 0) {
				Service service = servicedao.removeService(serviceId);
				if (service != null) {
					logger.info("Service deleted" + service);
					return mapper.map(service, ServiceDto.class);
				} else {
					throw new NotFoundException("The service With id " + serviceId + " is not found");
				}
			} else {
				throw new PricingException("Service Id must be greater than 0");
			}
		} else {
			throw new PricingConflictsException(
					"The service is used By one or more products ! please remove them before deleting");
		}

	}

	@Override
	public ServiceDto updateService(ServiceDto dto) {

		if (dto.getServiceId() > 0 && dto.getServiceName().matches("[A-Za-z ]{2,25}")) {
			Service service = mapper.map(dto, Service.class);
			service = servicedao.updateService(service);
			if (service != null) {
				logger.info("service updated" + service);
				return mapper.map(service, ServiceDto.class);
			} else {
				return null;
			}
		} else {
			throw new PricingException(
					"the service id must be greater than 0 and name must contain only spaces and numbers and be within 25 characters");
		}

	}

	@Override
	public ServiceDto getService(int serviceId) throws NotFoundException {

		if (serviceId > 0) {
			Service service = servicedao.getService(serviceId);
			if (service != null) {
				logger.info("service returned" + service);
				return mapper.map(service, ServiceDto.class);
			} else {
				throw new NotFoundException("The service With id " + serviceId + " is not found");
			}
		} else {
			throw new PricingException("The service id must be greater than 0");
		}

	}

	@Override
	public List<ServiceDto> getAllServices() {
		List<Service> list = servicedao.getAllServices();
		List<ServiceDto> dtolist = new ArrayList<>();
		if (!list.isEmpty()) {
			for (Service s : list) {
				dtolist.add(mapper.map(s, ServiceDto.class));
			}
		}
		return dtolist;
	}

	@Override
	public List<ProductDto> getAllProductsOfService(int serviceId) {

		if (serviceId > 0) {
			List<Product> list = prodServDao.getAllProductbyServiceId(serviceId);
			logger.info(list);
			List<ProductDto> dtolist = new ArrayList<>();
			if (!list.isEmpty()) {
				for (Product p : list) {
					dtolist.add(mapper.map(p, ProductDto.class));
				}
			}
			return dtolist;
		} else {
			throw new PricingException("service Id must be greater than 0");
		}

	}

	@Override
	public ServiceDto getServiceByName(String text) throws NotFoundException {
		Service service = servicedao.getServiceByName(text);
		if (service != null) {
			return mapper.map(service, ServiceDto.class);
		} else {
			throw new NotFoundException("Service With Name " + text + " doesn't exist");
		}
	}
}
