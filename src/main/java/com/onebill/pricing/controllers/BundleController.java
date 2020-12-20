package com.onebill.pricing.controllers;

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
import com.onebill.pricing.dto.ResponseDto;
import com.onebill.pricing.services.BundleManagerService;

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
		} else {
			resp.setError(true);
			resp.setResponse("Unable to add Bundle");
		}
		return resp;
	}

	@PutMapping("/bundle")
	public ResponseDto updateBundle(@RequestBody BundleDto dto) {
		ResponseDto resp = new ResponseDto();
		BundleDto bundle = service.updateBundle(dto);
		if (bundle != null) {
			resp.setResponse(bundle);
		} else {
			resp.setError(true);
			resp.setResponse("Unable to update Bundle");
		}
		return resp;
	}

	@GetMapping("/bundle/{id}")
	public ResponseDto updateBundle(@PathVariable Integer id) {
		ResponseDto resp = new ResponseDto();
		BundleDto bundle = service.getBundle(id);
		if (bundle != null) {
			resp.setResponse(bundle);
		} else {
			resp.setError(true);
			resp.setResponse("Unable to get Bundle");
		}
		return resp;
	}

	@DeleteMapping("/bundle/{id}")
	public ResponseDto deleteBundle(@PathVariable Integer id) {
		ResponseDto resp = new ResponseDto();
		BundleDto bundle = service.removeBundel(id);
		if (bundle != null) {
			resp.setResponse(bundle);
		} else {
			resp.setError(true);
			resp.setResponse("Unable to delete Bundle");
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
		} else {
			resp.setError(true);
			resp.setResponse("Unable to add Bundle product");
		}
		return resp;
	}

	@GetMapping("/bundle/product/{id}")
	public ResponseDto getBundleProduct(@PathVariable Integer id) {
		ResponseDto resp = new ResponseDto();
		BundleProductDto bp = service.getBundleProduct(id);
		if (bp != null) {
			resp.setResponse(bp);
		} else {
			resp.setError(true);
			resp.setResponse("Unable to get Bundle product");
		}
		return resp;
	}

	@DeleteMapping("/bundle/product/{id}")
	public ResponseDto deleteBundleProduct(@PathVariable Integer id) {
		ResponseDto resp = new ResponseDto();
		BundleProductDto bp = service.removeBundleProduct(id);
		if (bp != null) {
			resp.setResponse(bp);
		} else {
			resp.setError(true);
			resp.setResponse("Unable to get Bundle product");
		}
		return resp;
	}

}
