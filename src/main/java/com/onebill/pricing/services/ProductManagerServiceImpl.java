package com.onebill.pricing.services;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onebill.pricing.dao.AdditionalPriceDao;
import com.onebill.pricing.dao.ProductDao;
import com.onebill.pricing.dao.ProductPriceDao;
import com.onebill.pricing.dao.ProductServiceDao;
import com.onebill.pricing.dto.AdditionalPriceDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ProductPriceDto;
import com.onebill.pricing.dto.ProductServiceDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.entities.AdditionalPrice;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.entities.ProductPrice;
import com.onebill.pricing.entities.ProductService;

@Service
public class ProductManagerServiceImpl implements ProductManagerService {

	@Autowired
	ProductDao productdao;

	@Autowired
	ProductServiceDao prodServDao;

	@Autowired
	ProductPriceDao priceDao;

	@Autowired
	AdditionalPriceDao expDao;

	@Autowired
	private ModelMapper mapper;

	Logger logger = Logger.getLogger(ProductManagerServiceImpl.class);

	@Override
	public ProductDto addProduct(ProductDto dto) {
		Product product = mapper.map(dto, Product.class);
		productdao.addProduct(product);
		if (product != null) {
			logger.info("Product Added" + product);
			return mapper.map(product, ProductDto.class);
		} else {
			return null;
		}

	}

	@Override
	public ProductDto removeProductById(int productId) {
		Product product = productdao.removeProductById(productId);
		if (product != null) {
			logger.info("Product Deleted" + product);
			return mapper.map(product, ProductDto.class);
		} else {
			return null;
		}

	}

	@Override
	public ProductDto updateProduct(ProductDto dto) {
		Product product = mapper.map(dto, Product.class);
		productdao.updateProduct(product);
		if (product != null) {
			logger.info("Product Updated" + product);
			return mapper.map(product, ProductDto.class);
		} else {
			return null;

		}
	}

	@Override
	public ProductDto getProduct(int productId) {
		Product product = productdao.getProduct(productId);
		logger.info(product);
		if (product != null) {
			return mapper.map(product, ProductDto.class);
		} else {
			return null;

		}
	}

	@Override
	public List<ProductDto> getAllProducts() {

		List<Product> list = productdao.getAllProducts();
		List<ProductDto> dtolist = new ArrayList<>();
		if (!list.isEmpty()) {
			for (Product p : list) {
				dtolist.add(mapper.map(p, ProductDto.class));
			}
		}
		return dtolist;

	}

	@Override
	public ProductServiceDto addProductService(ProductServiceDto dto) {
		ProductService ps = mapper.map(dto, ProductService.class);
		prodServDao.addProductService(ps);
		if (ps != null) {
			logger.info("added Product Service" + ps);
			return mapper.map(ps, ProductServiceDto.class);
		} else {
			return null;
		}
	}

	@Override
	public ProductServiceDto updateProductService(ProductServiceDto dto) {
		ProductService ps = mapper.map(dto, ProductService.class);
		prodServDao.updateProductService(ps);
		if (ps != null) {
			logger.info("added Product Service" + ps);
			return mapper.map(ps, ProductServiceDto.class);
		} else {
			return null;
		}
	}

	@Override
	public ProductServiceDto getProductService(int psId) {
		ProductService ps = prodServDao.getProductServiceById(psId);
		if (ps != null) {
			logger.info("Found product Service" + ps);
			return mapper.map(ps, ProductServiceDto.class);
		} else {
			return null;
		}
	}

	@Override
	public ProductServiceDto deleteProductServiceById(int psId) {
		ProductService ps = prodServDao.removeProductServiceById(psId);
		if (ps != null) {
			logger.info("Deleted product service" + ps);
			return mapper.map(ps, ProductServiceDto.class);
		} else {
			return null;
		}
	}

	@Override
	public List<ServiceDto> getAllServicesofProduct(int productId) {
		List<com.onebill.pricing.entities.Service> list = prodServDao.getAllServicesOfProduct(productId);
		logger.info(list);
		List<ServiceDto> dtolist = new ArrayList<>();
		if (!list.isEmpty()) {
			for (com.onebill.pricing.entities.Service s : list) {
				dtolist.add(mapper.map(s, ServiceDto.class));
			}
		}
		return dtolist;
	}

	@Override
	public AdditionalPriceDto addAddlPrice(AdditionalPriceDto dto) {
		AdditionalPrice price = mapper.map(dto, AdditionalPrice.class);
		price = expDao.addAddlPrice(price);
		if (price != null) {
			logger.info("Added extra price" + price);
			return mapper.map(price, AdditionalPriceDto.class);
		} else {
			return null;
		}
	}

	@Override
	public AdditionalPriceDto removeAddlPriceById(int expId) {
		AdditionalPrice price = expDao.removeAddlPriceById(expId);
		if (price != null) {
			logger.info("Deleted extra price" + price);
			return mapper.map(price, AdditionalPriceDto.class);
		} else {
			return null;
		}

	}

	@Override
	public List<AdditionalPriceDto> getAddlPriceByProductId(int productId) {

		List<AdditionalPrice> list = expDao.getAddlPriceByProductId(productId);
		List<AdditionalPriceDto> dtolist = new ArrayList<>();
		if (!list.isEmpty()) {
			for (AdditionalPrice e : list) {
				dtolist.add(mapper.map(e, AdditionalPriceDto.class));
			}
		}
		return dtolist;
	}

	@Override
	public AdditionalPriceDto getAddlPriceById(int expId) {

		AdditionalPrice exp = expDao.getAddlPriceById(expId);
		if (exp != null) {
			return mapper.map(exp, AdditionalPriceDto.class);
		} else {
			return null;
		}

	}

	@Override
	public AdditionalPriceDto updateAddlPrice(AdditionalPriceDto dto) {
		AdditionalPrice exp = mapper.map(dto, AdditionalPrice.class);
		expDao.updateAddlPrice(exp);
		if (exp != null) {
			return mapper.map(exp, AdditionalPriceDto.class);
		} else {

			return null;
		}
	}

	@Override
	public ProductPriceDto addProductPrice(ProductPriceDto dto) {
		ProductPrice price = mapper.map(dto, ProductPrice.class);
		priceDao.addProductPrice(price);
		if (price != null) {
			return mapper.map(price, ProductPriceDto.class);
		} else {
			return null;
		}
	}

	@Override
	public ProductPriceDto updateProductPrice(ProductPriceDto dto) {
		ProductPrice price = mapper.map(dto, ProductPrice.class);
		priceDao.updateProductPrice(price);
		if (price != null) {
			return mapper.map(price, ProductPriceDto.class);
		} else {
			return null;
		}
	}

	@Override
	public ProductPriceDto getProuctPriceById(int productId) {
		ProductPrice price = priceDao.getProductPriceById(productId);
		logger.info(price);
		if (price != null) {
			return mapper.map(price, ProductPriceDto.class);
		} else {
			return null;
		}
	}

	@Override
	public ProductPriceDto getProductPrice(int productPriceId) {
		ProductPrice price = priceDao.getProductPrice(productPriceId);
		if (price != null) {
			return mapper.map(price, ProductPriceDto.class);
		} else {
			return null;
		}
	}

	@Override
	public List<ProductServiceDto> getAllProductService() {
		List<ProductService> list = prodServDao.getAllProductServices();
		List<ProductServiceDto> dtolist = new ArrayList<>();
		if (!list.isEmpty()) {
			for (ProductService p : list) {
				dtolist.add(mapper.map(p, ProductServiceDto.class));
			}
		}
		return dtolist;
	}

	@Override
	public List<ProductServiceDto> getAllProductServiceByProdId(int prodId) {
		List<ProductService> list = prodServDao.getAllProductServicesByProductId(prodId);
		List<ProductServiceDto> dtolist = new ArrayList<>();
		if (!list.isEmpty()) {
			for (ProductService p : list) {
				dtolist.add(mapper.map(p, ProductServiceDto.class));
			}
		}
		return dtolist;
	}

	@Override
	public List<ProductServiceDto> getAllProductServiceByServId(int servId) {
		List<ProductService> list = prodServDao.getAllProductServicesByServiceId(servId);
		List<ProductServiceDto> dtolist = new ArrayList<>();
		if (!list.isEmpty()) {
			for (ProductService p : list) {
				dtolist.add(mapper.map(p, ProductServiceDto.class));
			}
		}
		return dtolist;
	}

}
