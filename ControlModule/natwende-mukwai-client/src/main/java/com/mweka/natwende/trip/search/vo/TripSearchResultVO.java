package com.mweka.natwende.trip.search.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mweka.natwende.route.vo.RouteStopLinkVO;
import com.mweka.natwende.types.OperatorName;

@XmlRootElement(name = "TripSearchResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class TripSearchResultVO extends TripSearchVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6167874632050198143L;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm");

	@XmlTransient
	@JsonIgnore
	private List<RouteStopLinkVO> routeStopLinks;
	
	private Date estimatedJourneyStartDate, estimatedJouneyEndDate;
	private OperatorName operatorName;
	private long id;
	
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getEstimatedJourneyStartDateAsString() {
		if (estimatedJourneyStartDate == null) {
			return StringUtils.EMPTY;
		}
		return dateFormat.format(estimatedJourneyStartDate);
	}	
	
	public String getEstimatedJourneyEndDateAsString() {
		if (estimatedJouneyEndDate == null) {
			return StringUtils.EMPTY;
		}
		return dateFormat.format(estimatedJouneyEndDate);
	}

	public OperatorName getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(OperatorName operatorName) {
		this.operatorName = operatorName;
	}
	
}
