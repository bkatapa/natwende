package com.mweka.natwende.route.vo;

import java.util.List;

import com.mweka.natwende.base.vo.BaseVO;

public class RouteVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7066690683505705555L;
	
	private String name;
	private StopVO start;
	private StopVO stop;
	private RouteVO mirrorRoute;
	private List<RouteStopLinkVO> routeStops;
	
	public RouteVO() {
	}
	
	public RouteVO(String name, StopVO start, StopVO finalStop) {
		this.name = name;
		this.start = start;
		this.stop = finalStop;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public StopVO getStart() {
		return start;
	}
	
	public void setStart(StopVO start) {
		this.start = start;
	}
	
	public StopVO getStop() {
		return stop;
	}
	
	public void setStop(StopVO stop) {
		this.stop = stop;
	}
	
	public RouteVO getMirrorRoute() {
		return mirrorRoute;
	}

	public void setMirrorRoute(RouteVO mirrorRoute) {
		this.mirrorRoute = mirrorRoute;
	}

	public List<RouteStopLinkVO> getRouteStops() {
		return routeStops;
	}
	
	public void setRouteStops(List<RouteStopLinkVO> routeStops) {
		this.routeStops = routeStops;
	}
	
}
