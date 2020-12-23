package com.onebill.pricing.dto;

import java.util.List;

import lombok.Data;

@Data
public class BundleDto {

	private int bundleId;

	private String bundleName;

	private String bundleType;

	private List<BundleProductDto> bundleProducts;

}
