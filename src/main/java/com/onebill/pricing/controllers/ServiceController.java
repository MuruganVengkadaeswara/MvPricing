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

import com.onebill.pricing.dto.ResponseDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.services.ServiceManagerService;

@RestController
@RequestMapping("/services")
public class ServiceController {

	@Autowired
	ServiceManagerService service;

	@GetMapping
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

	@GetMapping("/{id}")
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

	@PostMapping
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

	@PutMapping
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

	@DeleteMapping("/{id}")
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

}
