package com.onebill.pricing.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onebill.pricing.dto.ResponseDto;
import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.servicebuilders.ServiceBuilder;

@RestController
public class TestController {


	@GetMapping("/testget")
	public ResponseDto getAllServices() {
		List<ServiceDto> list = new ArrayList<>();
		list = ServiceBuilder.getServices().getAllServices();
		ResponseDto res = new ResponseDto();
		res.setResponse(list);
		return res;

	}

}
