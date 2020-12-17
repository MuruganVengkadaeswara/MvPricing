package com.onebill.pricing.dto;

import lombok.Data;

@Data
public class ExtraPriceDto {

	private int extraId;

	private double price;

	private String description;

	private ProductDto product;

	private int productId;

}
