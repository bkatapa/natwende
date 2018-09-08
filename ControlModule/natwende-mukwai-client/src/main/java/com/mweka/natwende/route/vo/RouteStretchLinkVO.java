package com.mweka.natwende.route.vo;

import com.mweka.natwende.base.vo.BaseVO;

public class RouteStretchLinkVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8823299746168656263L;

	private RouteVO route;
	private StretchVO stretch;
	
	public RouteStretchLinkVO() {}
	
	public RouteStretchLinkVO(RouteVO route, StretchVO stretch) {
		this.route = route;
		this.stretch = stretch;
	}

	public RouteVO getRoute() {
		return route;
	}

	public void setRoute(RouteVO route) {
		this.route = route;
	}

	public StretchVO getStretch() {
		return stretch;
	}

	public void setStretch(StretchVO stretch) {
		this.stretch = stretch;
	}
	
}
