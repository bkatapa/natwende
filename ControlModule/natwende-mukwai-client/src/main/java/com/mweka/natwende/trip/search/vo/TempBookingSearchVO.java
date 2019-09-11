package com.mweka.natwende.trip.search.vo;

import com.mweka.natwende.types.SeatStatus;
import com.mweka.natwende.util.BaseSearchVO;

public class TempBookingSearchVO extends BaseSearchVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4723708157351933587L;
	
	private Long tripId;
	private String wsSessionId;
	private String seatNo;
	private SeatStatus seatStatus;
	
	public TempBookingSearchVO() {		
	}
	
	public TempBookingSearchVO(String wsSessionId) {
		this.wsSessionId = wsSessionId;
	}
	
	public boolean hasFilters() {
		if (tripId != null) {
			return true;
		}
		if (wsSessionId != null && !"".equals(wsSessionId.trim())) {
			return true;
		}
		if (seatNo != null && !"".equals(seatNo.trim())) {
			return true;
		}
		if (seatStatus != null) {
			return true;
		}
		return false;
	}

	public void clearSearch() {
		this.tripId = null;
		this.wsSessionId = null;
		this.seatNo = null;
		this.seatStatus = null;
	}

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public String getWsSessionId() {
		return wsSessionId;
	}

	public void setWsSessionId(String wsSessionId) {
		this.wsSessionId = wsSessionId;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public SeatStatus getSeatStatus() {
		return seatStatus;
	}

	public void setSeatStatus(SeatStatus seatStatus) {
		this.seatStatus = seatStatus;
	}
	
}
