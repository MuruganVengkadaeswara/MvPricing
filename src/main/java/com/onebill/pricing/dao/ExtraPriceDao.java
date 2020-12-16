package com.onebill.pricing.dao;

import java.util.List;

import com.onebill.pricing.entities.ExtraPrice;

public interface ExtraPriceDao {

	ExtraPrice addExtraPrice(ExtraPrice price);

	ExtraPrice updateExtraPrice(ExtraPrice price);

	ExtraPrice removeExtraPriceById(int id);

	ExtraPrice getExtraPrice(int id);

	List<ExtraPrice> getExtraPriceByProductId(int prodId);

}
