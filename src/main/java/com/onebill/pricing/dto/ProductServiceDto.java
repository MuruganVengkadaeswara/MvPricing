package com.onebill.pricing.dto;

import lombok.Data;

@Data
public class ProductServiceDto {

	private int psId;
	// private ProductDto product;
	private ServiceDto service;
	private int productId;
	private String serviceName;
	private int serviceId;
	private double servicePrice;
	private long freeUnits;
	private String unitType;

}
