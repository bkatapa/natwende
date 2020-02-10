package com.mweka.natwende.helper;

import java.util.Set;

import javax.faces.application.FacesMessage;

import com.mweka.natwende.base.vo.BaseVO;

public class SeatUpdateVO extends BaseVO  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3641546585342912482L;
	private Set<String> coordinates;
	private String action;
	private String bookingToken;
	private FacesMessage message;
	private int availSeats;
	
	public SeatUpdateVO() {			
	}
	
	public SeatUpdateVO(Set<String> coordinates, String action) {
		this.coordinates = coordinates;
		this.action = action;
	}
	
	public SeatUpdateVO(Set<String> coordinates, String action, String bookingToken) {
		this.coordinates = coordinates;
		this.action = action;
		this.bookingToken = bookingToken;
	}

	public Set<String> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Set<String> coordinates) {
		this.coordinates = coordinates;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public FacesMessage getMessage() {
		return message;
	}

	public void setMessage(FacesMessage message) {
		this.message = message;
	}

	public int getAvailSeats() {
		return availSeats;
	}

	public void setAvailSeats(int availSeats) {
		this.availSeats = availSeats;
	}

	public String getBookingToken() {
		return bookingToken;
	}

	public void setBookingToken(String bookingToken) {
		this.bookingToken = bookingToken;
	}
	
}
