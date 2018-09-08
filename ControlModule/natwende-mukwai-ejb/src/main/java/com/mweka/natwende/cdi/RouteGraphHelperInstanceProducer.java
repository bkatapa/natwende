package com.mweka.natwende.cdi;

import javax.enterprise.inject.Produces;

import com.mweka.natwende.util.StretchHelper;

public class RouteGraphHelperInstanceProducer {

	@Produces
	@StretchHelperInstance
	public StretchHelper newInstance() {
		return new StretchHelper();
	}
}
