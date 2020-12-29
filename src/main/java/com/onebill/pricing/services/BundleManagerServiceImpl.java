package com.onebill.pricing.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onebill.pricing.dao.BundleDao;
import com.onebill.pricing.dao.BundleProductDao;
import com.onebill.pricing.dao.ProductDao;
import com.onebill.pricing.dao.ProductServiceDao;
import com.onebill.pricing.dto.BundleDto;
import com.onebill.pricing.dto.BundleProductDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.entities.Bundle;
import com.onebill.pricing.entities.BundleProduct;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.exceptions.PricingConflictsException;
import com.onebill.pricing.exceptions.PricingException;
import com.onebill.pricing.exceptions.PricingNotFoundException;

import javassist.NotFoundException;

@Service
public class BundleManagerServiceImpl implements BundleManagerService {

	@Autowired
	BundleDao bundleDao;

	@Autowired
	BundleProductDao bundleProdDao;

	@Autowired
	ProductServiceDao prodServDao;

	@Autowired
	ProductDao prodDao;

	@Autowired
	ModelMapper mapper;

	@Override
	public BundleDto addBundle(BundleDto dto) {

		if (dto != null) {
			if (verifyBundleDto(dto)) {
				Bundle bundle = new Bundle();
				BeanUtils.copyProperties(dto, bundle, "bundleProducts");
				bundle = bundleDao.addBundle(bundle);

				List<BundleProductDto> list = dto.getBundleProducts();

				if (bundle != null) {
					if (list != null) {
						for (BundleProductDto bp : list) {
							BundleProduct p = mapper.map(bp, BundleProduct.class);
							p.setBundleId(bundle.getBundleId());
							bundleProdDao.addBundleProduct(p);
						}
					}
					return mapper.map(bundleDao.getBundle(bundle.getBundleId()), BundleDto.class);
				} else {
					return null;
				}
			} else {
				throw new PricingConflictsException("Unknown error while adding bundle");
			}

		} else {
			throw new PricingConflictsException("Bundle cannot be null");
		}

	}

	private boolean verifyBundleDto(BundleDto dto) {

		String[] plantypes = new String[] { "monthly", "yearly", "weekly", "daily" };

		if (dto.getBundleName() != null) {
			if (dto.getBundleProducts() != null) {
				List<BundleProductDto> list = dto.getBundleProducts();
				Set<BundleProductDto> set = new HashSet<>(list);
				for (BundleProductDto p : list) {
					if (prodDao.getProduct(p.getProductId()) == null) {
						throw new PricingConflictsException(
								"The product With Id " + p.getProductId() + " Doesn't exist");
					}
				}
				if (list.size() == set.size()) {
					if (bundleDao.getBundleByName(dto.getBundleName()) == null) {
						if (dto.getBundleName().length() < 25 && dto.getBundleName().length() > 2) {
							if (Arrays.stream(plantypes).anyMatch(dto.getBundleType().toLowerCase()::contains)) {
								return true;
							} else {
								throw new PricingConflictsException(
										"The bundle Type must either be monthly,yearly,weekly or daily");
							}
						} else {
							throw new PricingConflictsException("Bundle Name Must be within 2 and 25 characters");
						}
					} else {
						throw new PricingConflictsException(
								"Bundle with name " + dto.getBundleName() + " Already exists");
					}
				} else {
					throw new PricingConflictsException("Trying to add Duplicate Products , please Remove duplicates");
				}

			} else {
				throw new PricingConflictsException("Bundle Products Cannot be null");
			}
		} else {
			throw new PricingConflictsException("Bundle Name Cannot Be null");
		}

	}

	@Override
	public BundleDto updateBundle(BundleDto dto) {

		String[] plantypes = new String[] { "monthly", "yearly", "weekly", "daily" };

		if (dto.getBundleId() > 0) {
			if (dto.getBundleName().matches("[A-Za-z ]{2,25}")) {
				if (Arrays.stream(plantypes).anyMatch(dto.getBundleType().toLowerCase()::contains)) {

					Bundle bundle = mapper.map(dto, Bundle.class);
					bundle = bundleDao.updateBundle(bundle);
					if (bundle != null) {
						return mapper.map(bundle, BundleDto.class);
					} else {
						return null;
					}
				} else {
					throw new PricingConflictsException("Bundle Type must either be monthly,yearly,daily or weekly");
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
	public BundleDto getBundle(int id) throws NotFoundException {
		if (id > 0) {

			Bundle bundle = bundleDao.getBundle(id);
			if (bundle != null) {
				return mapper.map(bundle, BundleDto.class);
			} else {
				throw new NotFoundException("The bundle with Id " + id + "is not found");
			}
		} else {
			throw new PricingException("Bundle Id must be greater than 0");
		}
	}

	@Override
	public BundleDto removeBundel(int id){
		if (id > 0) {
			Bundle bundle = bundleDao.removeBundle(id);
			if (bundle != null) {
				return mapper.map(bundle, BundleDto.class);
			} else {
				throw new PricingNotFoundException("Bundle with Id " + id + " is not found");
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
			if (!prodServDao.getAllProductServicesByProductId(dto.getProductId()).isEmpty()) {
				BundleProduct bp = mapper.map(dto, BundleProduct.class);
				bp = bundleProdDao.addBundleProduct(bp);
				if (bp != null) {
					return mapper.map(bp, BundleProductDto.class);
				} else {
					return null;
				}
			} else {
				throw new PricingConflictsException(
						"The product to be added has no service please update the services");
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
	public List<ProductDto> getAllProductsOfbundle(int bundleId) {

		List<Product> list = bundleProdDao.getAllProductsOfbundle(bundleId);
		List<ProductDto> dtolist = new ArrayList<>();
		if (!list.isEmpty()) {
			for (Product p : list) {
				dtolist.add(mapper.map(p, ProductDto.class));
			}
			return dtolist;
		} else {
			throw new PricingNotFoundException("There are no products to this bundle");
		}
	}

	@Override
	public BundleDto getBundleByName(String text) {

		Bundle bundle = bundleDao.getBundleByName(text);
		if (bundle != null) {
			return mapper.map(bundle, BundleDto.class);
		}

		else {
			throw new PricingNotFoundException("Bundle with name " + text + " is not found");
		}

	}

	@Override
	public BundleProductDto removeProductOfBundle(int bundleId, int productId) throws NotFoundException {
		BundleProduct bp = bundleProdDao.removeProductOfBundle(productId, bundleId);
		if (bp != null) {
			return mapper.map(bp, BundleProductDto.class);
		} else {
			throw new NotFoundException("The product with id " + productId + " is not found to this bundle");
		}

	}

	@Override
	public List<BundleDto> searchBundleByName(String text) {
		List<Bundle> list = bundleDao.searchBundleByName(text);
		List<BundleDto> dtoList = new ArrayList<>();
		if (!list.isEmpty()) {
			for (Bundle b : list) {

				dtoList.add(mapper.map(b, BundleDto.class));
			}
			return dtoList;
		} else {
			throw new PricingNotFoundException("There Are no Bundles like " + text);
		}
	}

}
