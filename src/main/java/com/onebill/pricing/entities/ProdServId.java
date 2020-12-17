package com.onebill.pricing.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ProdServId implements Serializable {

	int productId;
	int serviceId;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + productId;
		result = prime * result + serviceId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdServId other = (ProdServId) obj;
		if (productId != other.productId)
			return false;
		if (serviceId != other.serviceId)
			return false;
		return true;
	}

}
