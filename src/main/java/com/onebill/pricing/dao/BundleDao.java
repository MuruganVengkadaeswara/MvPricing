package com.onebill.pricing.dao;

import java.util.List;

import com.onebill.pricing.entities.Bundle;

public interface BundleDao {

	public Bundle addBundle(Bundle bundle);

	public Bundle updateBundle(Bundle bundle);

	public Bundle getBundle(int bundleId);

	public Bundle removeBundle(int bundleId);

	public List<Bundle> getAllBundles();

}
