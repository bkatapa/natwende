package com.mweka.natwende.message;

import java.io.Serializable;

public class ReservationMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4098674086554304088L;

	private String tripSerialNo;
	private String seatNo;
	private String seatCoordinate;
	private Boolean selected = null;;
	private String fromTown;
	private String toTown;
	private String userId;
	
	public String getTripSerialNo() {
		return tripSerialNo;
	}
	
	public void setTripSerialNo(String tripSerialNo) {
		this.tripSerialNo = tripSerialNo;
	}
	
	public String getSeatNo() {
		return seatNo;
	}
	
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}
	
	public Boolean isSelected() {
		return selected;
	}
	
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	
	public String getFromTown() {
		return fromTown;
	}
	public void setFromTown(String fromTown) {
		this.fromTown = fromTown;
	}
	
	public String getToTown() {
		return toTown;
	}
	
	public void setToTown(String toTown) {
		this.toTown = toTown;
	}

	public String getSeatCoordinate() {
		return seatCoordinate;
	}

	public void setSeatCoordinate(String seatCoordinate) {
		this.seatCoordinate = seatCoordinate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}	
	
}
