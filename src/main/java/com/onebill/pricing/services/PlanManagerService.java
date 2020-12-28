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

	PlanDto getPlanByName(String text);

	List<PlanDto> searchPlanByName(String text);

	int getProductIdByPlanId(int planId);

	List<PlanDto> getAllPlans();

	List<ProductDto> getAllProductsOfPlan(int planId) throws NotFoundException;

}
