package com.onebill.pricing.services;

import java.util.List;

import com.onebill.pricing.dto.AdditionalPriceDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ProductPriceDto;
import com.onebill.pricing.dto.ProductServiceDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.entities.AdditionalPrice;
import com.onebill.pricing.entities.Product;

import javassist.NotFoundException;

public interface ProductManagerService {

	// product crud

	ProductDto addProduct(ProductDto dto);

	ProductDto removeProductById(int productId);

	ProductDto updateProduct(ProductDto dto);

	ProductDto getProduct(int productId);

	List<ProductDto> getAllProducts() throws NotFoundException;

	// product Service crud

	ProductServiceDto addProductService(ProductServiceDto dto);

	ProductServiceDto updateProductService(ProductServiceDto dto);

	ProductServiceDto getProductService(int psId);

	ProductServiceDto deleteProductServiceById(int psId);

	List<ServiceDto> getAllServicesofProduct(int productId);

	List<ProductServiceDto> getAllProductService();

	List<ProductServiceDto> getAllProductServiceByProdId(int prodId);

	List<ProductServiceDto> getAllProductServiceByServId(int servId);

	// price

	ProductPriceDto addProductPrice(ProductPriceDto dto);

	ProductPriceDto updateProductPrice(ProductPriceDto dto);

	ProductPriceDto getProuctPriceById(int productId);

	ProductPriceDto getProductPrice(int productPriceId);

	// Addl Price ops

	AdditionalPriceDto addAddlPrice(AdditionalPriceDto dto);

	AdditionalPriceDto removeAddlPriceById(int expId);

	List<AdditionalPriceDto> getAddlPriceByProductId(int productId);

	AdditionalPriceDto getAddlPriceById(int expId);

	AdditionalPriceDto updateAddlPrice(AdditionalPriceDto dto);

}
