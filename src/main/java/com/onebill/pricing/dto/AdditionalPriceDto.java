package com.onebill.pricing.dto;


import lombok.Data;

@Data
public class AdditionalPriceDto {

	private int addlPriceId;

	private double price;

	private String description;

//	private ProductDto product;

	private int productId;

}
