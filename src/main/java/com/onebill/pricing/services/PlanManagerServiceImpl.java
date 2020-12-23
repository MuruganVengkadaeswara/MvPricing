package com.onebill.pricing.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onebill.pricing.dao.PlanDao;
import com.onebill.pricing.dto.PlanDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.entities.Plan;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.exceptions.PricingConflictsException;
import com.onebill.pricing.exceptions.PricingException;

import javassist.NotFoundException;

@Service
public class PlanManagerServiceImpl implements PlanManagerService {

	@Autowired
	PlanDao plandao;

	@Autowired
	ModelMapper mapper;

	@Override
	public PlanDto addPlan(PlanDto dto) {

		String[] plantypes = new String[] { "monthly", "yearly", "weekly", "daily" };

		if (dto.getProductId() > 0) {
			if (Arrays.stream(plantypes).anyMatch(dto.getPlanType().toLowerCase()::contains)) {
				Plan plan = mapper.map(dto, Plan.class);
				plan = plandao.addPlan(plan);
				if (plan != null) {
					return mapper.map(plan, PlanDto.class);
				} else {
					return null;
				}
			} else {
				throw new PricingConflictsException("Plan Type must either be monthly,yearly,weekly or daily");
			}

		} else {
			throw new PricingException("Product id must be > 0");
		}

	}

	@Override
	public PlanDto updatePlan(PlanDto dto) {

		String[] plantypes = new String[] { "monthly", "yearly", "weekly", "daily" };

		if (dto.getPlanId() > 0) {
			if (Arrays.stream(plantypes).anyMatch(dto.getPlanType().toLowerCase()::contains)) {

				Plan plan = mapper.map(dto, Plan.class);
				plan = plandao.updatePlan(plan);
				if (plan != null) {
					return mapper.map(plan, PlanDto.class);
				} else {
					return null;
				}
			} else {
				throw new PricingConflictsException("Plan Type must either be monthly,yearly,weekly or daily");
			}
		} else {
			throw new PricingException("The plan id and validity days must be greater than 0");
		}

	}

	@Override
	public PlanDto deletePlan(int planId) {
		if (planId > 0) {
			Plan plan = plandao.removePlanbyId(planId);
			if (plan != null) {
				return mapper.map(plan, PlanDto.class);
			} else {
				return null;
			}
		} else {
			throw new PricingException("plan id must be > 0");
		}

	}

	@Override
	public PlanDto getPlan(int planId) {
		if (planId > 0) {
			Plan plan = plandao.getPlanById(planId);
			if (plan != null) {
				return mapper.map(plan, PlanDto.class);
			} else {
				return null;
			}
		} else {
			throw new PricingException("The Plan Id must be greater than 0");
		}

	}

	@Override
	public List<PlanDto> getAllPlans() {
		List<Plan> list = plandao.getAllPlans();
		List<PlanDto> dtolist = new ArrayList<>();
		if (!list.isEmpty()) {
			for (Plan p : list) {
				dtolist.add(mapper.map(p, PlanDto.class));
			}
		}
		return dtolist;
	}

	@Override
	public List<ProductDto> getAllProductsOfPlan(int planId) throws NotFoundException {
		List<Product> list = plandao.getAllProductsOfPlan(planId);
		List<ProductDto> dtolist = new ArrayList<>();
		if (!list.isEmpty()) {
			for (Product p : list) {
				dtolist.add(mapper.map(p, ProductDto.class));
			}
		}
		return dtolist;
	}

}
