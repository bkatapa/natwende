package com.mweka.natwende.trip.search.vo;

import java.util.Date;
import java.util.List;

import com.mweka.natwende.route.vo.RouteStopLinkVO;

public class TripSearchResultVO extends TripSearchVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6167874632050198143L;

	private List<RouteStopLinkVO> routeStopLinks;
	private Date estimatedJourneyStartDate, estimatedJouneyEndDate;
	
	public List<RouteStopLinkVO> getRouteStopLinks() {
		return routeStopLinks;
	}
	
	public void setRouteStopLinks(List<RouteStopLinkVO> routeStopLinks) {
		this.routeStopLinks = routeStopLinks;
	}
	
	public Date getEstimatedJourneyStartDate() {
		return estimatedJourneyStartDate;
	}
	
	public void setEstimatedJourneyStartDate(Date estimatedJourneyStartDate) {
		this.estimatedJourneyStartDate = estimatedJourneyStartDate;
	}
	
	public Date getEstimatedJouneyEndDate() {
		return estimatedJouneyEndDate;
	}
	
	public void setEstimatedJouneyEndDate(Date estimatedJouneyEndDate) {
		this.estimatedJouneyEndDate = estimatedJouneyEndDate;
	}	
	
}
