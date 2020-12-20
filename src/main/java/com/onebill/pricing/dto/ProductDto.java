package com.onebill.pricing.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductDto {

	private int productId;

	private String productName;

	private ProductPriceDto price;

	private List<AdditionalPriceDto> additionalPrices;

	private List<ProductServiceDto> services;

}
