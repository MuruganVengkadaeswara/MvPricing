package com.onebill.pricing.servicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

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
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.onebill.pricing.PricingAppConfiguration;
import com.onebill.pricing.dao.PlanDao;
import com.onebill.pricing.dao.ProductDao;
import com.onebill.pricing.dao.ProductServiceDao;
import com.onebill.pricing.dto.PlanDto;
import com.onebill.pricing.dto.ProductDto;
import com.onebill.pricing.dto.ProductPriceDto;
import com.onebill.pricing.entities.Plan;
import com.onebill.pricing.entities.Product;
import com.onebill.pricing.exceptions.PricingConflictsException;
import com.onebill.pricing.exceptions.PricingException;
import com.onebill.pricing.exceptions.PricingNotFoundException;
import com.onebill.pricing.services.PlanManagerService;
import com.onebill.pricing.services.PlanManagerServiceImpl;
import com.onebill.pricing.services.ProductManagerService;

import javassist.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class TestPlanManagerservice {

	@Mock
	PlanDao plandao;

	@Mock
	ProductDao prodDao;

	@Mock
	ProductServiceDao prodServDao;

	@Mock
	ProductManagerService productService;

	@InjectMocks
	PlanManagerService service = new PlanManagerServiceImpl();

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	Logger logger = Logger.getLogger(TestPlanManagerservice.class);

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
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
	public void testUpdateNonExistingPlan() {

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
	public void testGetNonExistingPlanByName() {
		Mockito.when(methodCall)
	}
	// @Test
	// public void addPlanWithoutProduct() {
	// expectedEx.expect(PricingConflictsException.class);
	// expectedEx.expectMessage("Product Must not be null");
	// PlanDto dto = new PlanDto();
	// dto.setPlanType("Monthly");
	// service.addPlan(dto);
	// }
	//
	// @Test
	// public void addPlanWithProduct() {
	//
	// }
	//
	// @Test
	// public void addProductToPlanWithoutProductPrice() {
	// expectedEx.expect(PricingConflictsException.class);
	// ProductDto dto = addDummyProduct("dummy");
	// addDummyPlan(dto.getProductId(), "monthly");
	//
	// }
	//
	// @Test
	// public void testaddPlanWithoutProductId() {
	// expectedEx.expect(PricingException.class);
	// expectedEx.expectMessage("Product id must be > 0");
	// PlanDto plan = new PlanDto();
	// plan.setPlanType("Monthly");
	// service.addPlan(plan);
	// }
	//
	// @Test
	// public void testAddPlanWithNonExistingProductId() {
	// expectedEx.expect(PricingConflictsException.class);
	// addDummyPlan(9999, "monthly");
	// }
	//
	// @Test
	// public void testAddPlanWithInvalidPlanType() {
	// expectedEx.expect(PricingConflictsException.class);
	// ProductDto p = addDummyProduct("dummy");
	// addDummyPlan(p.getProductId(), "((#(&eo[we]]");
	// }
	//
	// @Test
	// public void addPlanWithPrice() {
	// expectedEx.expect(PricingConflictsException.class);
	// ProductDto p = addDummyProduct("dummy");
	// ProductPriceDto price = new ProductPriceDto();
	// price.setPrice(400);
	// price.setProductId(p.getProductId());
	//
	// PlanDto plan = addDummyPlan(p.getProductId(), "monthly");
	//
	// assertTrue(plan.getPlanId() > 0);
	// assertEquals(p.getProductId(), plan.getProductId());
	// }
	//
	// @Test
	// public void updatePlanWithInvalidPlanType() {
	//
	// expectedEx.expect(PricingConflictsException.class);
	//
	// ProductDto p = addDummyProduct("dummy");
	// ProductPriceDto price = new ProductPriceDto();
	// price.setPrice(400);
	// price.setProductId(p.getProductId());
	//
	// PlanDto plan = addDummyPlan(p.getProductId(), "monthly");
	// plan.setPlanType("(9i4jsidjflksdjfl03i4j8riw");
	// service.updatePlan(plan);
	//
	// }
	//
	// public ProductDto addDummyProduct(String name) {
	// ProductDto p = new ProductDto();
	// p.setProductName(name);
	// return null;
	// }
	//
	// public PlanDto addDummyPlan(int productId, String planType) {
	// PlanDto plan = new PlanDto();
	// plan.setProductId(productId);
	// plan.setPlanType(planType);
	// return service.addPlan(plan);
	// }

}
