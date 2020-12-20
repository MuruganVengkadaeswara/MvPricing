package com.onebill.pricing.dto;

import java.util.List;


import lombok.Data;

@Data
public class BundleDto {

	private int bundleId;

	private String bundleName;
	
	private int validityDays;

	private List<BundleProductDto> bundleProducts;


}
