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
import com.onebill.pricing.entities.Bundle;
import com.onebill.pricing.entities.BundleProduct;

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
		Bundle bundle = mapper.map(dto, Bundle.class);
		bundle = bundleDao.addBundle(bundle);
		if (bundle != null) {
			return mapper.map(bundle, BundleDto.class);
		} else {
			return null;
		}
	}

	@Override
	public BundleDto updateBundle(BundleDto dto) {
		Bundle bundle = mapper.map(dto, Bundle.class);
		bundle = bundleDao.updateBundle(bundle);
		if (bundle != null) {
			return mapper.map(bundle, BundleDto.class);
		} else {
			return null;
		}
	}

	@Override
	public BundleDto getBundle(int id) {
		Bundle bundle = bundleDao.getBundle(id);
		if (bundle != null) {
			return mapper.map(bundle, BundleDto.class);
		} else {
			return null;
		}
	}

	@Override
	public BundleDto removeBundel(int id) {
		Bundle bundle = bundleDao.removeBundle(id);
		if (bundle != null) {
			return mapper.map(bundle, BundleDto.class);
		} else {
			return null;
		}
	}

	@Override
	public List<BundleDto> getAllBundles() {
		List<Bundle> bundles = bundleDao.getAllBundles();
		List<BundleDto> dtolist = new ArrayList<>();
		if (!bundles.isEmpty()) {
			for (Bundle b : bundles) {
				dtolist.add(mapper.map(b, BundleDto.class));
			}
		}
		return dtolist;
	}

	@Override
	public BundleProductDto addBundleProduct(BundleProductDto dto) {
		BundleProduct bp = mapper.map(dto, BundleProduct.class);
		bp = bundleProdDao.addBundleProduct(bp);
		if (bp != null) {
			return mapper.map(bp, BundleProductDto.class);
		} else {

			return null;
		}
	}

	@Override
	public BundleProductDto removeBundleProduct(int id) {
		BundleProduct bp = bundleProdDao.removeBundleProduct(id);
		if (bp != null) {
			return mapper.map(bp, BundleProductDto.class);
		} else {
			return null;
		}
	}

	@Override
	public BundleProductDto getBundleProduct(int id) {
		BundleProduct bp = bundleProdDao.getBundleProductById(id);
		if (bp != null) {
			return mapper.map(bp, BundleProductDto.class);
		} else {
			return null;
		}
	}

}
