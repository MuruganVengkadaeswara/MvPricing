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

import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ResponseDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.services.ServiceManagerService;

@RestController
public class ServiceController {

	@Autowired
	ServiceManagerService service;

	@GetMapping("/services")
	public ResponseDto getAllServices() {
		ResponseDto resp = new ResponseDto();
		List<ServiceDto> dtolist = service.getAllServices();
		if (!dtolist.isEmpty()) {
			resp.setResponse(dtolist);
		} else {
			resp.setError(true);
			resp.setResponse("unable to fetch all services");
		}
		return resp;
	}

	@GetMapping("/service/{id}")
	public ResponseDto getService(@PathVariable int id) {
		ResponseDto resp = new ResponseDto();
		ServiceDto dto = service.getService(id);
		if (dto != null) {
			resp.setResponse(dto);
		} else {
			resp.setError(true);
			resp.setResponse("unable to get service");
		}
		return resp;
	}

	@PostMapping("/service")
	public ResponseDto addService(@RequestBody ServiceDto dto) {
		ResponseDto resp = new ResponseDto();
		ServiceDto serv = service.addService(dto);
		if (serv != null) {
			resp.setResponse(serv);
		} else {
			resp.setError(true);
			resp.setResponse("Unable to add service");
		}
		return resp;
	}

	@PutMapping("/service")
	public ResponseDto updateService(@RequestBody ServiceDto dto) {
		ResponseDto resp = new ResponseDto();
		ServiceDto serv = service.updateService(dto);
		if (serv != null) {
			resp.setResponse(serv);
		} else {
			resp.setError(true);
			resp.setResponse("Unable to update service");
		}
		return resp;
	}

	@DeleteMapping("/service/{id}")
	public ResponseDto deleteService(@PathVariable int id) {
		ResponseDto resp = new ResponseDto();
		ServiceDto serv = service.removeService(id);
		if (serv != null) {
			resp.setResponse(serv);
		} else {
			resp.setError(true);
			resp.setResponse("Unable to delete service");
		}
		return resp;
	}

	@GetMapping("/service/{id}/products")
	public ResponseDto getAllProductsOfService(@PathVariable int id) {
		ResponseDto resp = new ResponseDto();
		List<ProductDto> list = service.getAllProductsOfService(id);
		if (!list.isEmpty()) {
			resp.setResponse(list);
		} else {
			resp.setError(true);
			resp.setResponse("unable to get all products");
		}
		return resp;

	}

}
