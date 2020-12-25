package com.onebill.pricing.services;

import java.util.List;

import com.onebill.pricing.dto.PlanDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.entities.Plan;

import javassist.NotFoundException;

public interface PlanManagerService {

	PlanDto addPlan(PlanDto dto);

	PlanDto updatePlan(PlanDto dto);

	PlanDto deletePlan(int planId);

	PlanDto getPlan(int planId);

	PlanDto getPlanByName(String text) throws NotFoundException;

	List<PlanDto> searchPlanByName(String text) throws NotFoundException;

	int getProductIdByPlanId(int planId) throws NotFoundException;

	List<PlanDto> getAllPlans();

	List<ProductDto> getAllProductsOfPlan(int planId) throws NotFoundException;

}
