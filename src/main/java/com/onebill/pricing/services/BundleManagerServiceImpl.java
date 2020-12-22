package com.onebill.pricing.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onebill.pricing.dao.BundleDao;
import com.onebill.pricing.dao.BundleProductDao;
import com.onebill.pricing.dto.BundleDto;
import com.onebill.pricing.dto.BundleProductDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.entities.Bundle;
import com.onebill.pricing.entities.BundleProduct;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.exceptions.PricingConflictsException;
import com.onebill.pricing.exceptions.PricingException;

import javassist.NotFoundException;

@Service
public class BundleManagerServiceImpl implements BundleManagerService {

	@Autowired
	BundleDao bundleDao;

	@Autowired
	BundleProductDao bundleProdDao;

	@Autowired
	ModelMapper mapper;

	@Override
	public BundleDto addBundle(BundleDto dto) {
		if (dto.getBundleName().matches("[A-Za-z ]{2,25}")) {
			Bundle bundle = mapper.map(dto, Bundle.class);
			bundle = bundleDao.addBundle(bundle);
			if (bundle != null) {
				return mapper.map(bundle, BundleDto.class);
			} else {
				return null;
			}
		} else {
			throw new PricingConflictsException(
					"Bundle Name Must be only numbers and characters andd within 2 and 25 characters");
		}

	}

	@Override
	public BundleDto updateBundle(BundleDto dto) {
		if (dto.getBundleId() > 0) {
			if (dto.getBundleName().matches("[A-Za-z ]{2,25}")) {
				Bundle bundle = mapper.map(dto, Bundle.class);
				bundle = bundleDao.updateBundle(bundle);
				if (bundle != null) {
					return mapper.map(bundle, BundleDto.class);
				} else {
					return null;
				}
			} else {
				throw new PricingConflictsException(
						"Bundle Name Must be only numbers and characters andd within 2 and 25 characters");
			}
		} else {
			throw new PricingConflictsException("bundle Id must be greater than 0");
		}

	}

	@Override
	public BundleDto getBundle(int id) {
		if (id > 0) {

			Bundle bundle = bundleDao.getBundle(id);
			if (bundle != null) {
				return mapper.map(bundle, BundleDto.class);
			} else {
				return null;
			}
		} else {
			throw new PricingException("Bundle Id must be greater than 0");
		}
	}

	@Override
	public BundleDto removeBundel(int id) {
		if (id > 0) {
			Bundle bundle = bundleDao.removeBundle(id);
			if (bundle != null) {
				return mapper.map(bundle, BundleDto.class);
			} else {
				return null;
			}
		} else {
			throw new PricingException("Bundle Id must be greater than 0");
		}

	}

	@Override
	public List<BundleDto> getAllBundles() throws NotFoundException {
		List<Bundle> bundles = bundleDao.getAllBundles();
		List<BundleDto> dtolist = new ArrayList<>();
		if (!bundles.isEmpty()) {
			for (Bundle b : bundles) {
				dtolist.add(mapper.map(b, BundleDto.class));
			}
			return dtolist;
		} else {
			throw new NotFoundException("There are no bundles");
		}
	}

	@Override
	public BundleProductDto addBundleProduct(BundleProductDto dto) {
		if (dto.getBundleId() > 0 && dto.getProductId() > 0) {
			BundleProduct bp = mapper.map(dto, BundleProduct.class);
			bp = bundleProdDao.addBundleProduct(bp);
			if (bp != null) {
				return mapper.map(bp, BundleProductDto.class);
			} else {
				return null;
			}
		} else {
			throw new PricingException("Bundle id and product id must be > 0");
		}

	}

	@Override
	public BundleProductDto removeBundleProduct(int id) {
		if (id > 0) {
			BundleProduct bp = bundleProdDao.removeBundleProduct(id);
			if (bp != null) {
				return mapper.map(bp, BundleProductDto.class);
			} else {
				return null;
			}
		} else {
			throw new PricingException("Id must be greater than 0");
		}

	}

	@Override
	public BundleProductDto getBundleProduct(int id) {
		if (id > 0) {
			BundleProduct bp = bundleProdDao.getBundleProductById(id);
			if (bp != null) {
				return mapper.map(bp, BundleProductDto.class);
			} else {
				return null;
			}
		} else {
			throw new PricingException("Bundle id must be > 0");
		}

	}

	@Override
	public List<ProductDto> getAllProductsOfbundle(int bundleId)  {

		List<Product> list = bundleProdDao.getAllProductsOfbundle(bundleId);
		List<ProductDto> dtolist = new ArrayList<>();
		if (!list.isEmpty()) {
			for (Product p : list) {
				dtolist.add(mapper.map(p, ProductDto.class));
			}
		} 
		return dtolist;
	}

}
