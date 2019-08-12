package com.mweka.natwende.route.search.vo;

import com.mweka.natwende.route.vo.RouteVO;
import com.mweka.natwende.route.vo.StopVO;
import com.mweka.natwende.util.BaseSearchVO;

public class StretchSearchVO extends BaseSearchVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2763113744950458059L;
	
	private StopVO from, to;
	private RouteVO route;
	
	public StretchSearchVO() {		
	}
	
	public StretchSearchVO(StopVO from, StopVO to) {
		this.from = from;
		this.to = to;
	}
	
	public boolean hasFilters() {
        if (from != null && from.getId() > 0L) {
            return true;
        }
        if (to != null && to.getId() > 0L) {
            return true;
        }
        if (route != null && route.getId() > 0L) {
            return true;
        }
        return false;
    }
	
	public void clearSearch() {
        this.from = null;
        this.to = null;
        this.route = null;
    }

	public StopVO getFrom() {
		return from;
	}

	public void setFrom(StopVO from) {
		this.from = from;
	}

	public StopVO getTo() {
		return to;
	}

	public void setTo(StopVO to) {
		this.to = to;
	}

	public RouteVO getRoute() {
		return route;
	}

	public void setRoute(RouteVO route) {
		this.route = route;
	}	
	
}
