package com.onebill.pricing.dto;

import lombok.Data;

@Data
public class ProductPriceDto {

	private int prodPriceId;

	private int productId;

	private ProductDto product;

	private double price;

}
