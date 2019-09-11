package com.mweka.natwende.trip.vo;

import java.util.Date;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.types.SeatStatus;

public class TempBookingVO extends BaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5527539255494512188L;

	private String wsSessionId;
	private Long tripId;
	private String seatNo;
	private Date lastHeartBeat;
	private SeatStatus seatStatus;
	
	public String getWsSessionId() {
		return wsSessionId;
	}
	
	public void setWsSessionId(String wsSessionId) {
		this.wsSessionId = wsSessionId;
	}
	
	public Long getTripId() {
		return tripId;
	}
	
	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}
	
	public String getSeatNo() {
		return seatNo;
	}
	
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}
	
	public Date getLastHeartBeat() {
		return lastHeartBeat;
	}
	
	public void setLastHeartBeat(Date lastHeartBeat) {
		this.lastHeartBeat = lastHeartBeat;
	}
	public SeatStatus getSeatStatus() {
		return seatStatus;
	}
	
	public void setSeatStatus(SeatStatus seatStatus) {
		this.seatStatus = seatStatus;
	}	
	
}
