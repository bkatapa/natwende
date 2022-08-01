package com.mweka.natwende.route.vo;

import com.mweka.natwende.base.vo.BaseVO;

public class FareRouteLinkVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2594253234572991865L;
	private FareVO fare;
	private RouteVO route;
	
	public FareRouteLinkVO(FareVO fare, RouteVO route) {
		super();
		this.route = route;
		this.fare = fare;
	}

	public FareRouteLinkVO() {
		super();
	}

	public FareVO getFare() {
		return fare;
	}

	public void setFare(FareVO fare) {
		this.fare = fare;
	}

	public RouteVO getRoute() {
		return route;
	}

	public void setRoute(RouteVO route) {
		this.route = route;
	}
	
}
