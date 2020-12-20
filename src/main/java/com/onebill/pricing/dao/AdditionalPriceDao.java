package com.onebill.pricing.dao;

import java.util.List;

import com.onebill.pricing.entities.AdditionalPrice;

public interface AdditionalPriceDao {

	AdditionalPrice addAddlPrice(AdditionalPrice price);

	AdditionalPrice updateAddlPrice(AdditionalPrice price);

	AdditionalPrice removeAddlPriceById(int id);

	AdditionalPrice getAddlPriceById(int id);

	List<AdditionalPrice> getAddlPriceByProductId(int prodId);

}
