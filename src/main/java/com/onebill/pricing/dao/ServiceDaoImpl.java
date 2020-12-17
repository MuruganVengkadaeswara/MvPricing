package com.onebill.pricing.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.onebill.pricing.entities.Service;

@Repository
public class ServiceDaoImpl implements ServiceDao {

	@PersistenceContext
	EntityManager manager;

	@Override
	@Transactional
	public Service addService(Service service) {
		manager.persist(service);
		return service;
	}

	@Override
	@Transactional
	public Service removeService(int serviceId) {
		Service service = manager.find(Service.class, serviceId);
		if (service != null) {
			manager.remove(service);
			return service;
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public Service updateService(Service service) {
		Service service1 = manager.find(Service.class, service.getServiceId());
		if (service1 != null) {
			BeanUtils.copyProperties(service, service1);
			return service1;
		} else {
			return null;
		}
	}

	@Override
	public Service getService(int serviceId) {
		return manager.find(Service.class, serviceId);
	}

	@Override
	public List<Service> getAllServices() {
		List<Service> list;
		TypedQuery<Service> query = manager.createQuery("FROM Service", Service.class);
		list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		} else {
			return null;
		}
	}

}
