package com.mweka.natwende.trip.vo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.operator.vo.OperatorVO;
import com.mweka.natwende.route.vo.RouteVO;
import com.mweka.natwende.types.DaysOfWeek;

public class TripScheduleVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5191260519393654716L;

	private Date startDate;
	private Date endDate;
	private Date scheduledDepartureTime;
	private OperatorVO operator;
	private RouteVO route;
	private List<DaysOfWeek> frequency;
	private List<BusTripScheduleLinkVO> busTripScheduleLinkList;
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getScheduledDepartureTime() {
		return scheduledDepartureTime;
	}
	
	public void setScheduledDepartureTime(Date scheduledDepartureTime) {
		this.scheduledDepartureTime = scheduledDepartureTime;
	}
	
	public OperatorVO getOperator() {
		return operator;
	}
	
	public void setOperator(OperatorVO operator) {
		this.operator = operator;
	}
	
	public RouteVO getRoute() {
		return route;
	}
	
	public void setRoute(RouteVO route) {
		this.route = route;
	}
	
	public List<DaysOfWeek> getFrequency() {
		return frequency;
	}
	
	public void setFrequency(List<DaysOfWeek> frequency) {
		this.frequency = frequency;
	}
	
	public List<BusTripScheduleLinkVO> getBusTripScheduleLinkList() {
		if (busTripScheduleLinkList == null) {
			busTripScheduleLinkList = new ArrayList<>();
		}
		return busTripScheduleLinkList;
	}
	
	public void setBusTripScheduleLinkList(List<BusTripScheduleLinkVO> busTripScheduleLinkList) {
		this.busTripScheduleLinkList = busTripScheduleLinkList;
	}
	
	public String getStartDateAsString() {
		return formatDate("dd/MM/yyyy", startDate);
	}
	
	public String getEndDateAsString() {
		return formatDate("dd/MM/yyyy", endDate);
	}
	
	public String getScheduledDepartureTimeAsString() {
		return formatDate("HH:mm", scheduledDepartureTime);
	}
	
	public String getFormattedFrequency() {
		if (frequency == null || frequency.isEmpty()) {
			return StringUtils.EMPTY;
		}
		StringBuilder sb = new StringBuilder();
		for (BusTripScheduleLinkVO link : getBusTripScheduleLinkList()) {
			sb.append(link.getBus().getReg()).append(" (").append(link.getDriver()).append("), ");
		}
		if (sb.length() > 3) {
			sb.delete(sb.length() - 2, sb.length() - 1);
		}
		return sb.toString();
	}
	
	public String getBusListAsString() {
		if (getBusTripScheduleLinkList().isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (BusTripScheduleLinkVO link : getBusTripScheduleLinkList()) {
			if (link.getBus() != null) {
				sb.append(link.getBus().getReg()).append(", ");
			}
		}
		return sb.delete(sb.length() - 2, sb.length() - 1).toString();
		
	}
	
	private String formatDate(String format, Date date) {
		return startDate == null ? "" : new SimpleDateFormat(format).format(date);
	}	
	
}
