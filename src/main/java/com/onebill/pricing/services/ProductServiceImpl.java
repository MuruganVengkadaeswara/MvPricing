package com.onebill.pricing.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.onebill.pricing.dao.ServiceDao;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ProductServiceDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.entities.Service;

@org.springframework.stereotype.Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ServiceDao servicedao;

	@Autowired
	private ModelMapper mapper;

	@Override
	public ServiceDto addService(ServiceDto dto) {
		Service service = mapper.map(dto, Service.class);
		servicedao.addService(service);
		if (service != null) {
			System.out.println("MAP WORKING");
			return mapper.map(service, ServiceDto.class);
		} else {
			return null;
		}
	}

	@Override
	public ServiceDto removeService(int serviceId) {
		return null;
	}

	@Override
	public ServiceDto updateService(ServiceDto dto) {
		return null;
	}

	@Override
	public ServiceDto getService(int serviceId) {
		return null;
	}

	@Override
	public ProductDto addProduct(ProductDto dto) {
		return null;
	}

	@Override
	public ProductDto removeProduct(ProductDto dto) {
		return null;
	}

	@Override
	public ProductDto updateProduct(ProductDto dto) {
		return null;
	}

	@Override
	public ProductDto getProduct(int productId) {
		return null;
	}

	@Override
	public ProductServiceDto addProductService(ProductServiceDto dto) {
		return null;
	}

}
