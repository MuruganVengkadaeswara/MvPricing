package com.onebill.pricing.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.onebill.pricing.dto.BundleDto;
import com.onebill.pricing.dto.BundleProductDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ResponseDto;
import com.onebill.pricing.exceptions.PricingException;
import com.onebill.pricing.services.BundleManagerService;

import javassist.NotFoundException;

@RestController
public class BundleController {

	@Autowired
	BundleManagerService service;

	@PostMapping("/bundle")
	public ResponseDto addBundle(@RequestBody BundleDto dto) {
		ResponseDto resp = new ResponseDto();
		BundleDto bundle = service.addBundle(dto);
		if (bundle != null) {
			resp.setResponse(bundle);
			return resp;
		} else {
			throw new PricingException("unknown Error While adding Bundle");

		}
	}

	@GetMapping("/bundles")
	public ResponseDto getAllBundles() throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		List<BundleDto> list = service.getAllBundles();
		if (!list.isEmpty()) {
			resp.setResponse(list);
			return resp;
		} else {
			throw new NotFoundException("There are no Bundles");
		}

	}

	@PutMapping("/bundle")
	public ResponseDto updateBundle(@RequestBody BundleDto dto) {
		ResponseDto resp = new ResponseDto();
		BundleDto bundle = service.updateBundle(dto);
		if (bundle != null) {
			resp.setResponse(bundle);
		} else {
			throw new PricingException("unknown Error While Updating Bundle");

		}
		return resp;
	}

	@GetMapping("/bundle/{id}")
	public ResponseDto getBundle(@PathVariable Integer id) throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		BundleDto bundle = service.getBundle(id);
		if (bundle != null) {
			resp.setResponse(bundle);
			return resp;
		} else {
			throw new NotFoundException("The bundle with id " + id + " is not found");
		}
	}

	@DeleteMapping("/bundle/{id}")
	public ResponseDto deleteBundle(@PathVariable Integer id) throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		BundleDto bundle = service.removeBundel(id);
		if (bundle != null) {
			resp.setResponse(bundle);
		} else {
			throw new PricingException("unknown Error While deleting Bundle");
		}
		return resp;
	}

	// Bundle products

	@PostMapping("/bundle/product")
	public ResponseDto addBundleProduct(@RequestBody BundleProductDto dto) {
		ResponseDto resp = new ResponseDto();
		BundleProductDto bp = service.addBundleProduct(dto);
		if (bp != null) {
			resp.setResponse(bp);
			return resp;
		} else {
			throw new PricingException("unknown Error While adding Bundle product");
		}
	}

	@GetMapping("/bundle/product/{id}")
	public ResponseDto getBundleProduct(@PathVariable Integer id) throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		BundleProductDto bp = service.getBundleProduct(id);
		if (bp != null) {
			resp.setResponse(bp);
			return resp;
		} else {
			throw new NotFoundException("The bundle with id " + id + " is not found");
		}
	}

	@GetMapping("/bundle/{id}/products")
	public ResponseDto getBundleProducts(@PathVariable Integer id) throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		List<ProductDto> list = service.getAllProductsOfbundle(id);
		if (!list.isEmpty()) {
			resp.setResponse(list);
			return resp;
		} else {
			throw new NotFoundException("The bundle with id " + id + " is not found");
		}
	}

	@DeleteMapping("/bundle/product/{id}")
	public ResponseDto deleteBundleProduct(@PathVariable Integer id) {
		ResponseDto resp = new ResponseDto();
		BundleProductDto bp = service.removeBundleProduct(id);
		if (bp != null) {
			resp.setResponse(bp);
			return resp;
		} else {
			throw new PricingException("unknown Error While deleting Bundle");

		}
	}

	@DeleteMapping("/bundle/{bid}/product/{pid}")
	public ResponseDto deleteBundleProduct(@PathVariable int bid, @PathVariable int pid) throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		BundleProductDto bp = service.removeProductOfBundle(bid, pid);
		if (bp != null) {
			resp.setResponse(bp);
			return resp;
		} else {
			throw new PricingException("unknown Error While deleting Bundle");

		}
	}

}
