package com.onebill.pricing.services;

import java.util.List;

import com.onebill.pricing.dto.BundleDto;
import com.onebill.pricing.dto.BundleProductDto;
import com.onebill.pricing.dto.ProductDto;

import javassist.NotFoundException;

public interface BundleManagerService {

	public BundleDto addBundle(BundleDto dto);

	public BundleDto updateBundle(BundleDto dto);

	public BundleDto getBundle(int id) throws NotFoundException;

	public BundleDto removeBundel(int id) throws NotFoundException;

	public List<BundleDto> getAllBundles() throws NotFoundException;

	public List<ProductDto> getAllProductsOfbundle(int bundleId) throws NotFoundException;

	public BundleDto getBundleByName(String text) throws NotFoundException;

	// Bundle product ops

	public BundleProductDto addBundleProduct(BundleProductDto dto);

	public BundleProductDto removeBundleProduct(int id);

	public BundleProductDto getBundleProduct(int id);

	public BundleProductDto removeProductOfBundle(int bundleId, int productId) throws NotFoundException;

}
