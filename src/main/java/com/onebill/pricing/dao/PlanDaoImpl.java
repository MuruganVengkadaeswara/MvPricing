package com.onebill.pricing.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.onebill.pricing.entities.Plan;

@Repository
public class PlanDaoImpl implements PlanDao {

	@PersistenceContext
	EntityManager manager;

	@Override
	@Transactional
	public Plan addPlan(Plan plan) {
		manager.persist(plan);
		return plan;
	}

	@Override
	@Transactional
	public Plan removePlanbyId(int planId) {
		Plan plan = manager.find(Plan.class, planId);
		if (plan != null) {
			return plan;
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public Plan updatePlan(Plan plan) {
		Plan plan1 = manager.find(Plan.class, plan.getPlanId());
		if (plan1 != null) {
			BeanUtils.copyProperties(plan, plan1);
			return plan1;
		} else {
			return null;
		}
	}

	@Override
	public Plan getPlanById(int planId) {
		return manager.find(Plan.class, planId);
	}

}
