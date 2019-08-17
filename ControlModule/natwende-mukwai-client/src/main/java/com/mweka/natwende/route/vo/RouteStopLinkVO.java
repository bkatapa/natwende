package com.mweka.natwende.route.vo;

import com.mweka.natwende.base.vo.BaseVO;

public class RouteStopLinkVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2656895261969675501L;
	
	private int stationIndex;
	
	private RouteVO route;
	private StopVO stop;
	
	public RouteStopLinkVO(int stationIndex, RouteVO route, StopVO stop) {
		super();
		this.stationIndex = stationIndex;
		this.route = route;
		this.stop = stop;
	}
	
	public RouteStopLinkVO(RouteVO route, StopVO stop) {
		super();
		this.route = route;
		this.stop = stop;
	}

	public RouteStopLinkVO() {
		super();
	}

	public int getStationIndex() {
		return stationIndex;
	}

	public void setStationIndex(int stationIndex) {
		this.stationIndex = stationIndex;
	}

	public RouteVO getRoute() {
		return route;
	}

	public void setRoute(RouteVO route) {
		this.route = route;
	}

	public StopVO getStop() {
		return stop;
	}

	public void setStop(StopVO stop) {
		this.stop = stop;
	}

}
