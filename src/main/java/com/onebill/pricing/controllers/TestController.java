package com.onebill.pricing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.onebill.pricing.dao.ServiceDao;
import com.onebill.pricing.dto.ResponseDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.entities.Service;
import com.onebill.pricing.services.PriceService;

@RestController
@RequestMapping("/services")
public class TestController {

	@Autowired
	ServiceDao dao;

	@Autowired
	PriceService priceservice;

	@PostMapping
	public ResponseDto addService(@RequestBody ServiceDto service) {

		ResponseDto dto = new ResponseDto();
		ServiceDto serv = priceservice.addService(service);
		if (serv != null) {
			dto.setResponse(serv);
		} else {
			dto.setError(true);
			dto.setResponse("unable to add");
		}

		return dto;
	}

	@DeleteMapping("/{id}")
	public ResponseDto removeService(@PathVariable Integer id) {
		ResponseDto dto = new ResponseDto();
		Service serv = dao.removeService(id);
		if (serv != null) {
			dto.setResponse(serv);
		} else {
			dto.setError(true);
			dto.setResponse("unable to delete");
		}
		return dto;
	}

	@GetMapping("/{id}")
	public ResponseDto getService(@PathVariable Integer id) {
		ResponseDto dto = new ResponseDto();
		Service serv = dao.getService(id);
		if (serv != null) {
			dto.setResponse(serv);
		} else {
			dto.setError(true);
			dto.setResponse("unable to get");
		}
		return dto;
	}

	@ResponseBody
	@GetMapping
	public String greet() {
		return "Hello User";
	}

}
