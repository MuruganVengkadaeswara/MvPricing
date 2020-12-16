package com.onebill.pricing.dao;

import com.onebill.pricing.entities.Plan;

public interface PlanDao {

	Plan addPlan(Plan plan);

	Plan removePlanbyId(int planId);

	Plan updatePlan(Plan plan);

	Plan getPlanById(int planId);

}
