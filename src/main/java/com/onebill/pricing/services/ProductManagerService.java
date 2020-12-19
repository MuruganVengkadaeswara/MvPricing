package com.onebill.pricing.services;

import java.util.List;

import com.onebill.pricing.dto.ExtraPriceDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ProductPriceDto;
import com.onebill.pricing.dto.ProductServiceDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.entities.ExtraPrice;
import com.onebill.pricing.entities.Product;

public interface ProductManagerService {

	// product crud

	ProductDto addProduct(ProductDto dto);

	ProductDto removeProductById(int productId);

	ProductDto updateProduct(ProductDto dto);

	ProductDto getProduct(int productId);

	List<ProductDto> getAllProducts();

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

	// Extra Price ops

	ExtraPriceDto addExtraPrice(ExtraPriceDto dto);

	ExtraPriceDto removeExtraPriceById(int expId);

	List<ExtraPriceDto> getExtraPriceByProductId(int productId);

	ExtraPriceDto getExtraPriceById(int expId);

	ExtraPriceDto updateExtraPrice(ExtraPriceDto dto);

}
