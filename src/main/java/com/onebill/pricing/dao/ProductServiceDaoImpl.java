package com.onebill.pricing.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.onebill.pricing.entities.Product;
import com.onebill.pricing.entities.ProductService;
import com.onebill.pricing.entities.Service;

@Repository
public class ProductServiceDaoImpl implements ProductServiceDao {

	@PersistenceContext
	EntityManager manager;

	Logger logger = Logger.getLogger(ProductServiceDaoImpl.class);

	@Override
	@Transactional
	public ProductService addProductService(ProductService prodService) {
		manager.persist(prodService);
		return prodService;
	}

	@Override
	@Transactional
	public ProductService removeProductServiceById(int psId) {
		ProductService prodServ = manager.find(ProductService.class, psId);
		if (prodServ != null) {
			manager.remove(prodServ);
			return prodServ;
		} else {
			return null;
		}
	}

	@Override
	public ProductService getProductServiceById(int psId) {
		ProductService service = manager.find(ProductService.class, psId);
		if (service != null) {

			return service;
		} else {
			return null;
		}

	}

	@Override
	@Transactional
	public ProductService updateProductService(ProductService prodService) {
		ProductService prodService1 = manager.find(ProductService.class, prodService.getPsId());
		if (prodService1 != null) {
			BeanUtils.copyProperties(prodService, prodService1);
			return prodService1;
		} else {
			return null;
		}
	}

	@Override
	public List<Service> getAllServicesOfProduct(int prodId) {

		TypedQuery<Integer> query = manager.createQuery("select serviceId from ProductService where productId= :id",
				Integer.class);
		query.setParameter("id", prodId);
		List<Integer> idList = query.getResultList();
		logger.info(idList);
		List<Service> services = new ArrayList<>();
		for (int e : idList) {
			TypedQuery<Service> query1 = manager.createQuery("FROM Service where serviceId= :id", Service.class);
			query1.setParameter("id", e);
			services.add(query1.getResultList().get(0));
		}
		logger.info(services);
		return services;
	}

	@Override
	public List<Product> getAllProductbyServiceId(int servId) {
		TypedQuery<Integer> query = manager.createQuery("select productId from ProductService where serviceId= :id",
				Integer.class);
		query.setParameter("id", servId);
		List<Integer> idList = query.getResultList();
		logger.info(idList);
		List<Product> products = new ArrayList<>();
		for (int e : idList) {
			TypedQuery<Product> query1 = manager.createQuery("FROM Product where productId= :id", Product.class);
			query1.setParameter("id", e);
			products.add(query1.getResultList().get(0));
		}
		logger.info(products);
		return products;
	}

	@Override
	public List<ProductService> getAllProductServices() {
		TypedQuery<ProductService> query = manager.createQuery("FROM ProductService", ProductService.class);
		return query.getResultList();
	}

	@Override
	public List<ProductService> getAllProductServicesByProductId(int prodId) {
		TypedQuery<ProductService> query = manager.createQuery("FROM ProductService where productId= :id",
				ProductService.class);
		query.setParameter("id", prodId);
		return query.getResultList();
	}

	@Override
	public List<ProductService> getAllProductServicesByServiceId(int servId) {
		TypedQuery<ProductService> query = manager.createQuery("FROM ProductService where serviceId= :id",
				ProductService.class);
		query.setParameter("id", servId);
		return query.getResultList();
	}

	@Override
	@Transactional
	public List<ProductService> removeAllProductServicesByProductId(int prodId) {
		TypedQuery<Integer> query = manager.createQuery("select psId FROM ProductService where productId= :id",
				Integer.class);
		query.setParameter("id", prodId);
		List<Integer> list = query.getResultList();
		List<ProductService> slist = new ArrayList<>();
		if (!list.isEmpty()) {
			for (int id : list) {
				logger.error(id);
				slist.add(manager.find(ProductService.class, id));
				manager.remove(manager.find(ProductService.class, id));
			}
		} else {
			logger.error("LIST IS EMPTY");
		}
		return slist;
	}

}
