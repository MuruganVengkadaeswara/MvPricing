package com.onebill.pricing.servicebuilders;

import java.util.List;

import com.onebill.pricing.dto.ServiceDto;
import com.onebill.pricing.services.ServiceManagerServiceImpl;

public class ServiceBuilder {

	public static ServiceManagerServiceImpl getServices() {
		return new ServiceManagerServiceImpl();
	}

	public static List<ServiceDto> getAllServices() {
		return new ServiceManagerServiceImpl().getAllServices();
	}

	public static String returnString() {
		return "Hello World";
	}

}
