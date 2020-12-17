package com.onebill.pricing.services;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onebill.pricing.dao.ExtraPriceDao;
import com.onebill.pricing.dao.ProductDao;
import com.onebill.pricing.dao.ProductServiceDao;
import com.onebill.pricing.dto.ExtraPriceDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ProductServiceDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.entities.ExtraPrice;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.entities.ProductService;

@Service
public class ProductManagerServiceImpl implements ProductManagerService {

	@Autowired
	ProductDao productdao;

	@Autowired
	ProductServiceDao prodServDao;

	@Autowired
	ExtraPriceDao expDao;

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
		return null;
	}

	@Override
	public List<Product> getAllProductsOfService(int serviceId) {
		return null;
	}

	@Override
	public ExtraPriceDto addExtraPrice(ExtraPriceDto dto) {
		ExtraPrice price = mapper.map(dto, ExtraPrice.class);
		expDao.addExtraPrice(price);
		if (price != null) {
			logger.info("Added extra price" + price);
			return mapper.map(dto, ExtraPriceDto.class);
		} else {
			return null;
		}
	}

	@Override
	public ExtraPriceDto removeExtraPriceById(int expId) {
		ExtraPrice price = expDao.removeExtraPriceById(expId);
		if (price != null) {
			logger.info("Deleted extra price" + price);
			return mapper.map(price, ExtraPriceDto.class);
		} else {
			return null;
		}

	}

	@Override
	public List<ExtraPriceDto> getExtraPriceByProductId(int productId) {

		List<ExtraPrice> list = expDao.getExtraPriceByProductId(productId);
		List<ExtraPriceDto> dtolist = new ArrayList<>();
		if (!list.isEmpty()) {
			for (ExtraPrice e : list) {
				dtolist.add(mapper.map(e, ExtraPriceDto.class));
			}
		}
		return dtolist;
	}

	@Override
	public ExtraPriceDto getExtraPriceById(int expId) {

		ExtraPrice exp = expDao.getExtraPriceById(expId);
		if (exp != null) {
			return mapper.map(exp, ExtraPriceDto.class);
		} else {
			return null;
		}

	}

	@Override
	public ExtraPriceDto updateExtraPrice(ExtraPriceDto dto) {
		ExtraPrice exp = mapper.map(dto, ExtraPrice.class);
		expDao.updateExtraPrice(exp);
		if (exp != null) {
			return mapper.map(exp, ExtraPriceDto.class);
		} else {

			return null;
		}
	}

}
