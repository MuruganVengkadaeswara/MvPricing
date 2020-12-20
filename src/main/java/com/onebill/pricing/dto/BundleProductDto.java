package com.onebill.pricing.dto;

import lombok.Data;

@Data
public class BundleProductDto {

	private int bpId;

	private ProductDto product;

	private int productId;

	private BundleDto bundle;

	private int bundleId;
}
