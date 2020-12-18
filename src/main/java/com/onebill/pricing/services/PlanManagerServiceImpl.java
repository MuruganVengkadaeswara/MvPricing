package com.onebill.pricing.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onebill.pricing.dao.PlanDao;
import com.onebill.pricing.dto.PlanDto;
import com.onebill.pricing.entities.Plan;

@Service
public class PlanManagerServiceImpl implements PlanManagerService {

	@Autowired
	PlanDao plandao;

	@Autowired
	ModelMapper mapper;

	@Override
	public PlanDto addPlan(PlanDto dto) {
		Plan plan = mapper.map(dto, Plan.class);
		plan = plandao.addPlan(plan);
		if (plan != null) {
			return mapper.map(plan, PlanDto.class);
		} else {
			return null;
		}
	}

	@Override
	public PlanDto updatePlan(PlanDto dto) {
		Plan plan = mapper.map(dto, Plan.class);
		plan = plandao.updatePlan(plan);
		if (plan != null) {
			return mapper.map(plan, PlanDto.class);
		} else {
			return null;
		}
	}

	@Override
	public PlanDto deletePlan(int planId) {
		Plan plan = plandao.removePlanbyId(planId);
		if (plan != null) {
			return mapper.map(plan, PlanDto.class);
		} else {
			return null;
		}
	}

	@Override
	public PlanDto getPlan(int planId) {
		Plan plan = plandao.getPlanById(planId);
		if (plan != null) {
			return mapper.map(plan, PlanDto.class);
		} else {
			return null;
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

}
