package com.onebill.pricing.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.onebill.pricing.entities.Plan;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.entities.ProductPrice;
import com.onebill.pricing.exceptions.PricingException;

import javassist.NotFoundException;

@Repository
public class PlanDaoImpl implements PlanDao {

	@PersistenceContext
	EntityManager manager;

	@Override
	@Transactional
	public Plan addPlan(Plan plan) {

		TypedQuery<ProductPrice> query = manager.createQuery("From ProductPrice where productId= :id",
				ProductPrice.class);
		query.setParameter("id", plan.getProductId());
		if (!query.getResultList().isEmpty()) {
			manager.persist(plan);
			return plan;
		} else {
			throw new PricingException("The Product to be added has no price ! please update the price");
		}
	}

	@Override
	@Transactional
	public Plan removePlanbyId(int planId) {
		Plan plan = manager.find(Plan.class, planId);
		if (plan != null) {
			manager.remove(plan);
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

	@Override
	public List<Plan> getAllPlans() {
		TypedQuery<Plan> query = manager.createQuery("FROM Plan", Plan.class);
		return query.getResultList();
	}

	@Override
	public List<Product> getAllProductsOfPlan(int planId) throws NotFoundException {

		Plan plan = manager.find(Plan.class, planId);
		if (plan != null) {
			TypedQuery<Product> query = manager.createQuery("FROM Product where productId= :id", Product.class);
			query.setParameter("id", plan.getProductId());
			List<Product> list = query.getResultList();
			if (!list.isEmpty()) {
				return list;
			} else {
				throw new NotFoundException("There are no products to the plan");
			}

		} else {
			throw new NotFoundException("There is no Product with id " + planId);
		}
	}

	@Override
	public int getProductIdByPlanId(int planId) {
		if (manager.find(Plan.class, planId) != null) {
			return manager.find(Plan.class, planId).getProductId();

		} else {
			return 0;
		}
	}

}
