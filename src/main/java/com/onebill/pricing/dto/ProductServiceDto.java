package com.onebill.pricing.dto;

import lombok.Data;

@Data
public class ProductServiceDto {

	private int psId;
	private ProductDto product;
	private ServiceDto service;
	private double servicePrice;
	private long freeUnits;

}
