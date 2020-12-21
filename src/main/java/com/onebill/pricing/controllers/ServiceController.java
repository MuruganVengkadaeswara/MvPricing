package com.onebill.pricing.controllers;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ResponseDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.exceptions.PricingException;
import com.onebill.pricing.services.ServiceManagerService;

import javassist.NotFoundException;

@RestController
public class ServiceController {

	@Autowired
	ServiceManagerService service;

	@GetMapping("/services")
	public ResponseDto getAllServices() throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		List<ServiceDto> dtolist = service.getAllServices();
		if (!dtolist.isEmpty()) {
			resp.setResponse(dtolist);
			return resp;
		} else {
			throw new NotFoundException("There Are no services");
		}
	}

	@GetMapping("/service/{id}")
	public ResponseDto getService(@PathVariable int id) throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		ServiceDto dto = service.getService(id);
		if (dto != null) {
			resp.setResponse(dto);
			return resp;
		} else {
			throw new NotFoundException("Service with id " + id + " is not found");

		}
	}

	@PostMapping("/service")
	public ResponseDto addService(@RequestBody ServiceDto dto) {
		ResponseDto resp = new ResponseDto();
		ServiceDto serv = service.addService(dto);
		if (serv != null) {
			resp.setResponse(serv);
			return resp;
		} else {
			throw new PersistenceException();
		}
	}

	@PutMapping("/service")
	public ResponseDto updateService(@RequestBody ServiceDto dto) {
		ResponseDto resp = new ResponseDto();
		ServiceDto serv = service.updateService(dto);
		if (serv != null) {
			resp.setResponse(serv);
			return resp;
		} else {
			throw new PricingException("Unable to update the service");
		}
	}

	@DeleteMapping("/service/{id}")
	public ResponseDto deleteService(@PathVariable int id) {
		ResponseDto resp = new ResponseDto();
		ServiceDto serv = service.removeService(id);
		if (serv != null) {
			resp.setResponse(serv);
			return resp;
		} else {
			throw new PricingException("unable to delete the service");
		}
	}

	@GetMapping("/service/{id}/products")
	public ResponseDto getAllProductsOfService(@PathVariable int id) throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		List<ProductDto> list = service.getAllProductsOfService(id);
		if (!list.isEmpty()) {
			resp.setResponse(list);
			return resp;
		} else {
			throw new NotFoundException("There are no products");
		}

	}

}
