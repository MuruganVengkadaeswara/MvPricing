package com.onebill.pricing.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onebill.pricing.dao.PlanDao;
import com.onebill.pricing.dao.ProductDao;
import com.onebill.pricing.dao.ProductServiceDao;
import com.onebill.pricing.dto.PlanDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.entities.Plan;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.exceptions.PricingConflictsException;
import com.onebill.pricing.exceptions.PricingException;
import com.onebill.pricing.exceptions.PricingNotFoundException;

import javassist.NotFoundException;

@Service
public class PlanManagerServiceImpl implements PlanManagerService {

	@Autowired
	PlanDao plandao;

	@Autowired
	ProductDao prodDao;

	@Autowired
	ProductServiceDao prodServDao;

	@Autowired
	ProductManagerService productService;

	@Autowired
	ModelMapper mapper;

	@Override
	public PlanDto addPlan(PlanDto dto) {
		if (verifyPlanDto(dto)) {
			Plan plan = new Plan();
			if (dto.getProduct() != null) {
				ProductDto pdto = productService.addProduct(dto.getProduct());
				BeanUtils.copyProperties(dto, plan, "product");
				plan.setProductId(pdto.getProductId());
			} else {
				// if (plandao.getPlanIdByProductId(dto.getProductId()) < 0) {
				plan.setPlanName(dto.getPlanName());
				plan.setProductId(dto.getProductId());
				plan.setPlanType(dto.getPlanType());
				// } else {
				// throw new PricingConflictsException(
				// "There is already a plan with product Id " + dto.getProductId());
				// }
			}
			plan = plandao.addPlan(plan);
			if (plan != null) {
				return mapper.map(getPlan(plan.getPlanId()), PlanDto.class);
			} else {
				return null;
			}
		} else {
			throw new PricingConflictsException("Unknown Error while adding Plan");
		}

	}

	public boolean verifyPlanDto(PlanDto dto) {
		String[] plantypes = new String[] { "monthly", "yearly", "weekly", "daily" };

		if (dto.getPlanName() != null) {
			if (plandao.getPlanByName(dto.getPlanName()) == null) {
				if (dto.getPlanType() != null
						&& Arrays.stream(plantypes).anyMatch(dto.getPlanType().toLowerCase()::contains)) {
					if (dto.getProductId() > 0) {
						if (prodDao.getProduct(dto.getProductId()) != null) {
							return true;
						} else {
							throw new PricingConflictsException("Product Doesn't exist");
						}
					} else if (dto.getProduct() != null) {
						return true;
					} else {
						throw new PricingConflictsException("Please provide a product or product id");
					}
				} else {
					throw new PricingConflictsException("Plan Type must either be weekly,monthly,yearly or daily");
				}

			} else {
				throw new PricingConflictsException("Plan with name " + dto.getPlanName() + " Already exists");
			}
		} else {
			throw new PricingConflictsException("Plan Name Cannot be null");
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
			throw new PricingException("The plan id  must be greater than 0");
		}

	}

	@Override
	public PlanDto deletePlan(int planId) {
		if (planId > 0) {
			Plan plan = plandao.removePlanbyId(planId);
			if (plan != null) {
				return mapper.map(plan, PlanDto.class);
			} else {
				throw new PricingNotFoundException("Plan with id " + planId + " is not found");
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
				throw new PricingNotFoundException("Plan with id " + planId + " is not found");
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

	@Override
	public int getProductIdByPlanId(int planId) throws NotFoundException {
		int pId = plandao.getProductIdByPlanId(planId);
		if (pId > 0) {
			return pId;
		} else {
			throw new NotFoundException("Plan not found");
		}
	}

	@Override
	public PlanDto getPlanByName(String text) throws NotFoundException {
		Plan plan = plandao.getPlanByName(text);
		if (plan != null) {
			return mapper.map(plan, PlanDto.class);
		} else {
			throw new NotFoundException("Plan With Name " + text + "is not found");
		}
	}

	@Override
	public List<PlanDto> searchPlanByName(String text) throws NotFoundException {
		List<Plan> list = plandao.searchPlanByName(text);
		List<PlanDto> dtoList = new ArrayList<>();
		if (!list.isEmpty()) {
			for (Plan p : list) {
				dtoList.add(mapper.map(p, PlanDto.class));
			}
			return dtoList;
		} else {
			throw new NotFoundException("There are no plans like " + text);
		}
	}

}
