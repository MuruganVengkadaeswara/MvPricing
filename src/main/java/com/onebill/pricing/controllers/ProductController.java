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

import com.onebill.pricing.dto.ExtraPriceDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ProductPriceDto;
import com.onebill.pricing.dto.ProductServiceDto;
import com.onebill.pricing.dto.ResponseDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.services.ProductManagerService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ProductManagerService service;

	@GetMapping
	public ResponseDto getAllProducts() {
		ResponseDto resp = new ResponseDto();
		List<ProductDto> list = service.getAllProducts();
		if (!list.isEmpty()) {
			resp.setResponse(list);
		} else {
			resp.setError(true);
			resp.setResponse("unable to fetch product list");
		}
		return resp;
	}

	@PostMapping
	public ResponseDto addProduct(@RequestBody ProductDto dto) {
		ResponseDto resp = new ResponseDto();
		ProductDto dto1 = service.addProduct(dto);
		if (dto1 != null) {
			resp.setResponse(dto1);
		} else {
			resp.setError(true);
			resp.setResponse("unable to add product");
		}
		return resp;
	}

	@GetMapping("/{id}")
	public ResponseDto getProduct(@PathVariable int id) {
		ResponseDto resp = new ResponseDto();
		ProductDto dto = service.getProduct(id);
		if (dto != null) {
			resp.setResponse(dto);
		} else {
			resp.setError(true);
			resp.setResponse("unable to get product");
		}
		return resp;
	}

	@DeleteMapping("/{id}")
	public ResponseDto deleteProduct(@PathVariable int id) {
		ResponseDto resp = new ResponseDto();
		ProductDto dto = service.removeProductById(id);
		if (dto != null) {
			resp.setResponse(dto);
		} else {
			resp.setError(true);
			resp.setResponse("unable to delete product");
		}
		return resp;
	}

	@PutMapping
	public ResponseDto updateProduct(@RequestBody ProductDto dto) {
		ResponseDto resp = new ResponseDto();
		ProductDto dto1 = service.updateProduct(dto);
		if (dto1 != null) {
			resp.setResponse(dto1);
		} else {
			resp.setError(true);
			resp.setResponse("unable to add product");
		}
		return resp;
	}

	// product services

	@PostMapping("/service")
	public ResponseDto addProductService(@RequestBody ProductServiceDto dto) {
		ResponseDto resp = new ResponseDto();
		ProductServiceDto dto1 = service.addProductService(dto);
		if (dto1 != null) {
			resp.setResponse(dto1);
		} else {
			resp.setError(true);
			resp.setResponse("unable to add service to product");
		}
		return resp;
	}

	@PutMapping("/service")
	public ResponseDto updateProductService(@RequestBody ProductServiceDto dto) {
		ResponseDto resp = new ResponseDto();
		ProductServiceDto dto1 = service.updateProductService(dto);
		if (dto1 != null) {
			resp.setResponse(dto1);
		} else {
			resp.setError(true);
			resp.setResponse("unable to update service to product");
		}
		return resp;
	}

	@DeleteMapping("/services/{id}")
	public ResponseDto deleteProductService(@PathVariable int id) {
		ResponseDto resp = new ResponseDto();
		ProductServiceDto dto1 = service.deleteProductServiceById(id);
		if (dto1 != null) {
			resp.setResponse(dto1);
		} else {
			resp.setError(true);
			resp.setResponse("unable to delete service to product");
		}
		return resp;
	}

	@GetMapping("/services/{id}")
	public ResponseDto getProductService(@PathVariable int id) {
		ResponseDto resp = new ResponseDto();
		ProductServiceDto dto1 = service.getProductService(id);
		if (dto1 != null) {
			resp.setResponse(dto1);
		} else {
			resp.setError(true);
			resp.setResponse("unable to get service of product");
		}
		return resp;
	}

	@GetMapping("/{id}/services")
	public ResponseDto getAllServicesOfProduct(@PathVariable int id) {
		ResponseDto resp = new ResponseDto();
		List<ServiceDto> list = service.getAllServicesofProduct(id);
		if (!list.isEmpty()) {
			resp.setResponse(list);
		} else {
			resp.setError(true);
			resp.setResponse("unable to fetch all the services");
		}
		return resp;
	}

	// extra price

	@PostMapping("/extraprice")
	public ResponseDto addExtraPrice(@RequestBody ExtraPriceDto dto) {
		ResponseDto resp = new ResponseDto();
		ExtraPriceDto dto1 = service.addExtraPrice(dto);
		if (dto1 != null) {
			resp.setResponse(dto1);
		} else {
			resp.setError(true);
			resp.setResponse("unable to add extra price");
		}
		return resp;

	}

	@PutMapping("/extraprice")
	public ResponseDto updateExtraPrice(@RequestBody ExtraPriceDto dto) {
		ResponseDto resp = new ResponseDto();
		ExtraPriceDto dto1 = service.updateExtraPrice(dto);
		if (dto1 != null) {
			resp.setResponse(dto1);
		} else {
			resp.setError(true);
			resp.setResponse("unable to update extra price");
		}
		return resp;
	}

	@GetMapping("/extraprices/{id}")
	public ResponseDto getExtraPrice(@PathVariable int id) {
		ResponseDto resp = new ResponseDto();
		ExtraPriceDto dto1 = service.getExtraPriceById(id);
		if (dto1 != null) {
			resp.setResponse(dto1);
		} else {
			resp.setError(true);
			resp.setResponse("unable to get extra price");
		}
		return resp;
	}

	@DeleteMapping("/extraprices/{id}")
	public ResponseDto deleteExtraPrice(@PathVariable int id) {
		ResponseDto resp = new ResponseDto();
		ExtraPriceDto dto1 = service.removeExtraPriceById(id);
		if (dto1 != null) {
			resp.setResponse(dto1);
		} else {
			resp.setError(true);
			resp.setResponse("unable to delete extra price");
		}
		return resp;
	}

	@GetMapping("/{id}/extraprices")
	public ResponseDto getAllExtraPrices(@PathVariable int id) {
		ResponseDto resp = new ResponseDto();
		List<ExtraPriceDto> list = service.getExtraPriceByProductId(id);
		if (!list.isEmpty()) {
			resp.setResponse(list);
		} else {
			resp.setError(true);
			resp.setResponse("unable to fetch all extra prices");
		}
		return resp;
	}

	@PostMapping("/price")
	public ResponseDto addProductPrice(@RequestBody ProductPriceDto dto) {
		ResponseDto resp = new ResponseDto();
		ProductPriceDto price = service.addProductPrice(dto);
		if (price != null) {
			resp.setResponse(price);
		} else {
			resp.setError(true);
			resp.setResponse("unable to add price");
		}
		return resp;
	}

	@PutMapping("/price")
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

	@GetMapping("/{id}/price")
	public ResponseDto getProductPrice(@PathVariable int id) {
		ResponseDto resp = new ResponseDto();
		ProductPriceDto price = service.getProuctPriceById(id);
		if (price != null) {
			resp.setResponse(price);
		} else {
			resp.setError(true);
			resp.setResponse("unable to get price");
		}
		return resp;
	}

}
