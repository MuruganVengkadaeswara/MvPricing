package com.onebill.pricing.services;

import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ProductServiceDto;
import com.onebill.pricing.dto.ServiceDto;

public interface ProductService {

	// service crud

	ServiceDto addService(ServiceDto dto);

	ServiceDto removeService(int serviceId);

	ServiceDto updateService(ServiceDto dto);

	ServiceDto getService(int serviceId);

	// product crud

	ProductDto addProduct(ProductDto dto);

	ProductDto removeProduct(ProductDto dto);

	ProductDto updateProduct(ProductDto dto);

	ProductDto getProduct(int productId);

	// product Service crud

	ProductServiceDto addProductService(ProductServiceDto dto);
}
