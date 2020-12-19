package com.onebill.pricing.dto;

import lombok.Data;

@Data
public class ProductPriceDto {

	private int prodPriceId;

	private double price;
	
	private int productId;

	private ProductDto product;
}
