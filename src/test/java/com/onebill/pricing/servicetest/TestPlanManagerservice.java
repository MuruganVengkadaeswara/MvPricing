package com.onebill.pricing.servicetest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.logging.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import com.onebill.pricing.dao.PlanDao;
import com.onebill.pricing.dao.PlanDaoImpl;
import com.onebill.pricing.dao.ProductDao;
import com.onebill.pricing.dao.ProductServiceDao;
import com.onebill.pricing.dto.PlanDto;
import com.onebill.pricing.entities.Plan;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.exceptions.PricingConflictsException;
import com.onebill.pricing.exceptions.PricingException;
import com.onebill.pricing.exceptions.PricingNotFoundException;
import com.onebill.pricing.services.PlanManagerService;
import com.onebill.pricing.services.PlanManagerServiceImpl;
import com.onebill.pricing.services.ProductManagerService;
import com.onebill.pricing.services.ProductManagerServiceImpl;

import javassist.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class TestPlanManagerservice {

	@Mock
	EntityManager manager;

	@Mock
	PlanDao plandao;

	@Mock
	ProductDao prodDao;

	@Mock
	ProductServiceDao prodServDao;

	@InjectMocks
	ProductManagerService productService = new ProductManagerServiceImpl();

	@InjectMocks
	PlanManagerService service = new PlanManagerServiceImpl();

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	Logger logger = Logger.getLogger(TestPlanManagerservice.class);

	@Autowired
	ModelMapper mapper;

	@Test
	public void testAddPlanWithNullInput() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Plan Cannot be null");
		PlanDto dto = null;
		service.addPlan(dto);
	}

	@Test
	public void testAddPlanWithNullName() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Plan Name Cannot be null");
		PlanDto dto = new PlanDto();
		service.addPlan(dto);
	}

	@Test
	public void testAddExistingPlanName() {

		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Plan with name Hello Already exists");

		Mockito.when(plandao.getPlanByName("Hello")).thenReturn(new Plan());

		PlanDto dto = new PlanDto();
		dto.setPlanName("Hello");
		service.addPlan(dto);

	}

	@Test
	public void testAddPlanWithoutPlanType() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Plan Type must either be weekly,monthly,yearly or daily");
		PlanDto dto = new PlanDto();
		dto.setPlanName("Hello");
		service.addPlan(dto);
	}

	@Test
	public void testAddPlanWithoutProductOrProductId() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Please provide a product or product id");
		PlanDto dto = new PlanDto();
		dto.setPlanName("Hello");
		dto.setPlanType("Monthly");
		service.addPlan(dto);
	}

	@Test
	public void testAddPlanWithInvalidPlanType() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Plan Type must either be weekly,monthly,yearly or daily");
		PlanDto dto = new PlanDto();
		dto.setPlanName("Hello");
		dto.setPlanType("AKSHF*HOEQ");
		service.addPlan(dto);
	}

	@Test
	public void testAddPlanWithNegativeProductId() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Please provide a product or product id");
		PlanDto dto = new PlanDto();
		dto.setPlanName("Hello");
		dto.setPlanType("Monthly");
		dto.setProductId(-5);
		service.addPlan(dto);
	}

	@Test
	public void testAddPlanWithNonExistingProductId() {

		Mockito.when(prodDao.getProduct(5)).thenReturn(null);

		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Product Doesn't exist");
		PlanDto dto = new PlanDto();
		dto.setPlanName("Hello");
		dto.setPlanType("Monthly");
		dto.setProductId(5);
		service.addPlan(dto);
	}

	@Test
	public void testUpdatePlanWithNegativePlanId() {
		expectedEx.expect(PricingException.class);
		expectedEx.expectMessage("The plan id  must be greater than 0");
		PlanDto dto = new PlanDto();
		dto.setPlanId(-99);
		dto.setPlanName("Hello");
		dto.setPlanType("Monthly");
		dto.setProductId(5);
		service.updatePlan(dto);
	}

	@Test
	public void testUpdatePlanWithInvalidPlanType() {
		expectedEx.expect(PricingConflictsException.class);
		expectedEx.expectMessage("Plan Type must either be monthly,yearly,weekly or daily");
		PlanDto dto = new PlanDto();
		dto.setPlanId(9);
		dto.setPlanName("Hello");
		dto.setPlanType("AKSJDKA");
		dto.setProductId(5);
		service.updatePlan(dto);
	}

	@Test
	public void testAddPlan() {
		PlanDto dto = new PlanDto();
		dto.setPlanId(1);
		dto.setPlanName("dummy");
		dto.setPlanType("Monthly");
		dto.setProductId(1);
		Plan plan = new Plan();
		BeanUtils.copyProperties(dto, plan);
		when(prodDao.getProduct(1)).thenReturn(new Product());
		when(plandao.addPlan(any())).thenReturn(plan);
		PlanDto newdto = service.addPlan(dto);
		logger.info(newdto);
		logger.info(plandao.addPlan(plan));
	}

	@Test
	public void getProductIdByNonExistingPlanId() {
		expectedEx.expect(PricingNotFoundException.class);
		expectedEx.expectMessage("Plan not found");
		service.getProductIdByPlanId(99);
	}

	@Test
	public void deletePlanByNegativeId() {
		expectedEx.expect(PricingException.class);
		expectedEx.expectMessage("plan id must be > 0");
		service.deletePlan(-99);
	}

	@Test
	public void removeNonExistingPlan() {
		expectedEx.expect(PricingNotFoundException.class);
		expectedEx.expectMessage("Plan with id 9 is not found");
		Mockito.when(plandao.removePlanbyId(9)).thenReturn(null);
		service.deletePlan(9);
	}

	@Test
	public void getPlanByNegativeId() {
		expectedEx.expect(PricingException.class);
		expectedEx.expectMessage("The Plan Id must be greater than 0");
		service.getPlan(-99);
	}

	@Test
	public void getNonExistingPlan() {
		expectedEx.expect(PricingNotFoundException.class);
		expectedEx.expectMessage("Plan with id 9 is not found");
		Mockito.when(plandao.getPlanById(9)).thenReturn(null);
		service.getPlan(9);
	}

	@Test
	public void getAllPlansWithEmptyDb() {
		expectedEx.expect(PricingNotFoundException.class);
		expectedEx.expectMessage("There are no plans");
		List<Plan> list = new ArrayList<Plan>();
		plandao = Mockito.mock(PlanDao.class);
		Mockito.when(plandao.getAllPlans()).thenReturn(list);
		service.getAllPlans();
	}

	@Test
	public void getAllNonExistingProductsOfPlan() throws NotFoundException {
		expectedEx.expect(PricingNotFoundException.class);
		expectedEx.expectMessage("There are no products to this plan");
		List<Product> list = new ArrayList<>();
		Mockito.when(plandao.getAllProductsOfPlan(5)).thenReturn(list);
		service.getAllProductsOfPlan(5);
	}

	@Test
	public void getNonExistingPlanByName() {
		expectedEx.expect(PricingNotFoundException.class);
		expectedEx.expectMessage("Plan With Name Hello is not found");
		Mockito.when(plandao.getPlanByName("Hello")).thenReturn(null);
		service.getPlanByName("Hello");
	}

	@Test
	public void searchNonExistingPlanByName() {
		expectedEx.expect(PricingNotFoundException.class);
		expectedEx.expectMessage("There are no plans like Hello");
		List<Plan> list = new ArrayList<Plan>();
		when(plandao.searchPlanByName("Hello")).thenReturn(list);
		service.searchPlanByName("Hello");
	}

}
