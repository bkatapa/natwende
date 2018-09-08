package com.mweka.natwende.route.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.mweka.natwende.base.vo.BaseVO;

public class StretchVO extends BaseVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	private Date estimatedTravelTime;	
	private Date observedTravelTime;
	private BigDecimal distanceKM;
	private BigDecimal fareAmount;
	private StopVO from;
	private StopVO to;
	
	public StretchVO() {
	}
	
	public StretchVO(StopVO from,  StopVO to) {
		this.from = from;
		this.to = to;
	}
	
	public StretchVO(StopVO from,  StopVO to, Date estimatedTravelTime) {
		this.from = from;
		this.to = to;
		this.estimatedTravelTime = estimatedTravelTime;
	}

	public Date getEstimatedTravelTime() {
		return estimatedTravelTime;
	}

	public void setEstimatedTravelTime(Date estimatedTravelTime) {
		this.estimatedTravelTime = estimatedTravelTime;
	}	

	public Date getObservedTravelTime() {
		return observedTravelTime;
	}

	public void setObservedTravelTime(Date observedTravelTime) {
		this.observedTravelTime = observedTravelTime;
	}

	public BigDecimal getDistanceKM() {
		return distanceKM;
	}

	public void setDistanceKM(BigDecimal distanceKM) {
		this.distanceKM = distanceKM;
	}

	public BigDecimal getFareAmount() {
		return fareAmount;
	}

	public void setFareAmount(BigDecimal fareAmount) {
		this.fareAmount = fareAmount;
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
	
	public String getEndpointStations() {
		if (from == null || to == null) {
			return "";
		}
		return from + " -> " + to;
	}
	
	public String getEndpointTowns() {
		if (from == null || to == null) {
			return "";
		}
		if (from.getTown() == null || to.getTown() == null) {
			return "";
		}
		return from.getTown() + " -> " + to.getTown();
	}
	
}
