package com.onebill.pricing.services;

import java.util.List;

import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ServiceDto;

import javassist.NotFoundException;

public interface ServiceManagerService {

	// service crud

	ServiceDto addService(ServiceDto dto);

	ServiceDto removeService(int serviceId) throws NotFoundException;

	ServiceDto updateService(ServiceDto dto);

	ServiceDto getService(int serviceId);

	List<ServiceDto> getAllServices();

	List<ProductDto> getAllProductsOfService(int serviceId);

	ServiceDto getServiceByName(String text);

}
