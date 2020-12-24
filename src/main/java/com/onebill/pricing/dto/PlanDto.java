package com.onebill.pricing.dto;

import lombok.Data;

@Data
public class PlanDto {

	private int planId;

	private ProductDto product;


	private int productId;

	private String planType;

}
