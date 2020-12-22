package com.onebill.pricing.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onebill.pricing.dto.AdditionalPriceDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ProductPriceDto;
import com.onebill.pricing.dto.ProductServiceDto;
import com.onebill.pricing.dto.ResponseDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.exceptions.PricingException;
import com.onebill.pricing.services.ProductManagerService;

import javassist.NotFoundException;

@RestController
public class ProductController {

	@Autowired
	ProductManagerService service;

	@GetMapping("/products")
	public ResponseDto getAllProducts() throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		List<ProductDto> list = service.getAllProducts();
		if (!list.isEmpty()) {
			resp.setResponse(list);
			return resp;
		} else {
			throw new NotFoundException("There Are no Products");
		}
	}

	@PostMapping("/product")
	public ResponseDto addProduct(@RequestBody ProductDto dto) {
		ResponseDto resp = new ResponseDto();
		ProductDto dto1 = service.addProduct(dto);
		if (dto1 != null) {
			resp.setResponse(dto1);
			return resp;
		} else {
			throw new PricingException("unknown Error While adding Product");

		}
	}

	@GetMapping("/product/{id}")
	public ResponseDto getProduct(@PathVariable int id) throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		ProductDto dto = service.getProduct(id);
		if (dto != null) {
			resp.setResponse(dto);
			return resp;
		} else {
			throw new NotFoundException("Product Not Found");
		}
	}

	@DeleteMapping("/product/{id}")
	public ResponseDto deleteProduct(@PathVariable int id) {
		ResponseDto resp = new ResponseDto();
		ProductDto dto = service.removeProductById(id);
		if (dto != null) {
			resp.setResponse(dto);
			return resp;
		} else {
			throw new PricingException("unknown Error While deleting Product");
		}

	}

	@PutMapping("/product")
	public ResponseDto updateProduct(@RequestBody ProductDto dto) {
		ResponseDto resp = new ResponseDto();
		ProductDto dto1 = service.updateProduct(dto);
		if (dto1 != null) {
			resp.setResponse(dto1);
			return resp;
		} else {
			throw new PricingException("unknown Error While updating Product");
		}
	}

	// product services

	@PostMapping("/product/service")
	public ResponseDto addProductService(@RequestBody ProductServiceDto dto) {
		ResponseDto resp = new ResponseDto();
		ProductServiceDto dto1 = service.addProductService(dto);
		if (dto1 != null) {
			resp.setResponse(dto1);
			return resp;
		} else {
			throw new PricingException("unknown Error While adding product service");

		}
	}

	@PutMapping("/product/service")
	public ResponseDto updateProductService(@RequestBody ProductServiceDto dto) {
		ResponseDto resp = new ResponseDto();
		ProductServiceDto dto1 = service.updateProductService(dto);
		if (dto1 != null) {
			resp.setResponse(dto1);
			return resp;
		} else {
			throw new PricingException("unknown Error While updating service of product");

		}
	}

	@DeleteMapping("/product/services/{id}")
	public ResponseDto deleteProductService(@PathVariable int id) {
		ResponseDto resp = new ResponseDto();
		ProductServiceDto dto1 = service.deleteProductServiceById(id);
		if (dto1 != null) {
			resp.setResponse(dto1);
			return resp;
		} else {
			throw new PricingException("unknown Error While deleting service of product");

		}
	}

	@GetMapping("/product/services/{id}")
	public ResponseDto getProductService(@PathVariable int id) throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		ProductServiceDto dto1 = service.getProductService(id);
		if (dto1 != null) {
			resp.setResponse(dto1);
			return resp;
		} else {
			throw new NotFoundException("The product service is not found");
		}
	}

	@GetMapping("/product/{id}/services")
	public ResponseDto getAllProductServices(@PathVariable int id) throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		List<ProductServiceDto> list = service.getAllProductServiceByProdId(id);
		if (!list.isEmpty()) {
			resp.setResponse(list);
			return resp;
		} else {
			throw new NotFoundException("There are no product services to the product " + id);

		}
	}

	@GetMapping("/product/services")
	public ResponseDto getAllProductServices() throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		List<ProductServiceDto> list = service.getAllProductService();
		if (!list.isEmpty()) {
			resp.setResponse(list);
			return resp;
		} else {
			throw new NotFoundException("There are no product services");
		}
	}

	// extra price

	@PostMapping("/product/addlprice")
	public ResponseDto addExtraPrice(@RequestBody AdditionalPriceDto dto) {
		ResponseDto resp = new ResponseDto();
		AdditionalPriceDto dto1 = service.addAddlPrice(dto);
		if (dto1 != null) {
			resp.setResponse(dto1);
		} else {
			resp.setError(true);
			resp.setResponse("unable to add extra price");
		}
		return resp;

	}

	@PutMapping("/product/addlprice")
	public ResponseDto updateExtraPrice(@RequestBody AdditionalPriceDto dto) {
		ResponseDto resp = new ResponseDto();
		AdditionalPriceDto dto1 = service.updateAddlPrice(dto);
		if (dto1 != null) {
			resp.setResponse(dto1);
			return resp;
		} else {
			throw new PricingException("Unknown error while updating additional price");
		}
	}

	@GetMapping("/product/addlprices/{id}")
	public ResponseDto getExtraPrice(@PathVariable int id) throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		AdditionalPriceDto dto1 = service.getAddlPriceById(id);
		if (dto1 != null) {
			resp.setResponse(dto1);
			return resp;
		} else {
			throw new NotFoundException("There are no additional prices to product");

		}
	}

	@DeleteMapping("/product/addlprices/{id}")
	public ResponseDto deleteExtraPrice(@PathVariable int id) {
		ResponseDto resp = new ResponseDto();
		AdditionalPriceDto dto1 = service.removeAddlPriceById(id);
		if (dto1 != null) {
			resp.setResponse(dto1);
			return resp;
		} else {
			throw new PricingException("Unknown error while deleting addl charge");
		}
	}

	@GetMapping("/product/{id}/addlprices")
	public ResponseDto getAllExtraPrices(@PathVariable int id) throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		List<AdditionalPriceDto> list = service.getAddlPriceByProductId(id);
		if (!list.isEmpty()) {
			resp.setResponse(list);
			return resp;
		} else {
			throw new NotFoundException("There are no additional price for product" + id);

		}
	}

	@PostMapping("/product/price")
	public ResponseDto addProductPrice(@RequestBody ProductPriceDto dto) {
		ResponseDto resp = new ResponseDto();
		ProductPriceDto price = service.addProductPrice(dto);
		if (price != null) {
			resp.setResponse(price);
			return resp;
		} else {
			throw new PricingException("Unknown error While adding product price");
		}
	}

	@PutMapping("/product/price")
	public ResponseDto updateProductPrice(@RequestBody ProductPriceDto dto) {
		ResponseDto resp = new ResponseDto();
		ProductPriceDto price = service.updateProductPrice(dto);
		if (price != null) {
			resp.setResponse(price);
		} else {
			resp.setError(true);
			resp.setResponse("unable to update price");
		}
		return resp;
	}

	@GetMapping("/product/{id}/price")
	public ResponseDto getProductPrice(@PathVariable int id) throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		ProductPriceDto price = service.getProuctPriceById(id);
		if (price != null) {
			resp.setResponse(price);
			return resp;
		} else {
			throw new NotFoundException("The product price with product id " + id + " is not found");
		}
	}

}
