package com.onebill.pricing.dao;

import java.util.List;

import com.onebill.pricing.entities.Service;

public interface ServiceDao {

	Service addService(Service service);

	Service removeService(int serviceId);

	Service updateService(Service service);

	Service getService(int serviceId);

	List<Service> getAllServices();

	Service getServiceByName(String text);

}
