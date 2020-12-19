package com.onebill.pricing.dao;

import java.util.List;

import com.onebill.pricing.entities.Plan;

public interface PlanDao {

	Plan addPlan(Plan plan);

	Plan removePlanbyId(int planId);

	Plan updatePlan(Plan plan);

	Plan getPlanById(int planId);

	List<Plan> getAllPlans();

}
