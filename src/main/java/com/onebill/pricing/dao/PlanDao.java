package com.onebill.pricing.dao;

import java.util.List;

import com.onebill.pricing.entities.Plan;
import com.onebill.pricing.entities.Product;

import javassist.NotFoundException;

public interface PlanDao {

	Plan addPlan(Plan plan);

	Plan removePlanbyId(int planId);

	Plan updatePlan(Plan plan);

	Plan getPlanById(int planId);

	int getProductIdByPlanId(int planId);

	List<Plan> getAllPlans();

	List<Product> getAllProductsOfPlan(int planId) throws NotFoundException;

}
