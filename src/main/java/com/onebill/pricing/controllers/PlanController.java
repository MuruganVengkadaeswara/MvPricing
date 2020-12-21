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

import com.onebill.pricing.dto.PlanDto;
import com.onebill.pricing.dto.ResponseDto;
import com.onebill.pricing.exceptions.PricingException;
import com.onebill.pricing.services.PlanManagerService;

import javassist.NotFoundException;

@RestController
public class PlanController {

	@Autowired
	PlanManagerService service;

	@PostMapping("/plan")
	public ResponseDto addPlan(@RequestBody PlanDto dto) {
		ResponseDto resp = new ResponseDto();
		PlanDto plan = service.addPlan(dto);
		if (plan != null) {
			resp.setResponse(plan);
			return resp;
		} else {
			throw new PricingException("Error While Adding Plan");
		}
	}

	@PutMapping("/plan")
	public ResponseDto updatePlan(@RequestBody PlanDto dto) {
		ResponseDto resp = new ResponseDto();
		PlanDto plan = service.updatePlan(dto);
		if (plan != null) {
			resp.setResponse(plan);
			return resp;
		} else {
			throw new PricingException("Error While Updating Product");

		}
	}

	@GetMapping("/plan/{id}")
	public ResponseDto getPlan(@PathVariable int id) throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		PlanDto plan = service.getPlan(id);
		if (plan != null) {
			resp.setResponse(plan);
			return resp;
		} else {
			throw new NotFoundException("The plan with " + id + " is not found");
		}
	}

	@DeleteMapping("/plan/{id}")
	public ResponseDto deletePlan(@PathVariable int id) {
		ResponseDto resp = new ResponseDto();
		PlanDto plan = service.deletePlan(id);
		if (plan != null) {
			resp.setResponse(plan);
			return resp;
		} else {
			throw new PricingException("Error While deleting Plan");

		}
	}

	@GetMapping("/plans")
	public ResponseDto getAllPlans() throws NotFoundException {
		ResponseDto resp = new ResponseDto();
		List<PlanDto> list = service.getAllPlans();
		if (!list.isEmpty()) {
			resp.setResponse(list);
			return resp;
		} else {
			throw new NotFoundException("There Are No plans");
		}
	}

}
