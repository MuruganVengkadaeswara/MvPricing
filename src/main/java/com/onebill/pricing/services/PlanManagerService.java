package com.onebill.pricing.services;

import java.util.List;

import com.onebill.pricing.dto.PlanDto;
import com.onebill.pricing.dto.ProductDto;

import javassist.NotFoundException;

public interface PlanManagerService {

	PlanDto addPlan(PlanDto dto);

	PlanDto updatePlan(PlanDto dto);

	PlanDto deletePlan(int planId);

	PlanDto getPlan(int planId);

	List<PlanDto> getAllPlans();

	List<ProductDto> getAllProductsOfPlan(int planId) throws NotFoundException;

}
