package com.mweka.natwende.route.vo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	@JsonIgnore	
	public Date getEstimatedTravelTime() {
		return estimatedTravelTime;
	}
	
	public String getEstimatedTravelTimeAsString() {
		if (estimatedTravelTime == null) {
			return "00hrs 00min";		
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(estimatedTravelTime);
		
		return c.get(Calendar.HOUR) + "Hrs " + c.get(Calendar.MINUTE) + "Min";
	}

	public void setEstimatedTravelTime(Date estimatedTravelTime) {
		this.estimatedTravelTime = estimatedTravelTime;
	}	

	@JsonIgnore
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof StretchVO)) {
			return false;
		}
		StretchVO other = (StretchVO) obj;
		if (this == other) {
			return true;
		}		
		if (this.getId() > -1L && this.getId() == other.getId()) {
			return true;
		}
		if (this.from == null || this.to == null) {
			return false;
		}
		if (this.from.getId() == other.from.getId() && this.to.getId() == other.to.getId()) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return getEndpointTowns();
	}
	
}
