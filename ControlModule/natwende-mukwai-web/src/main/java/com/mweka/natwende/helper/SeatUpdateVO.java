package com.mweka.natwende.helper;

import java.util.Set;

import javax.faces.application.FacesMessage;

import com.mweka.natwende.base.vo.BaseVO;

public class SeatUpdateVO extends BaseVO  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3641546585342912482L;
	private Set<String> selectableCoordinates, reservedCoordinates;
	private String action;	
	private String[] endpoints;
	private FacesMessage message;
	private int availSeats;
	private Set<String> userBookingTokens, allBookingTokens;
	
	public SeatUpdateVO() {			
	}
	
	public SeatUpdateVO(Set<String> coordinates, String action) {
		this.action = action;
		switch (action) {
		case "vacate" : // fall through
		case "reserve" : reservedCoordinates = coordinates;
		break;
		case "select" : selectableCoordinates = coordinates;
		break;
		}		
	}
	
	public SeatUpdateVO(Set<String> coordinates, String action, String[] endpoints) {
		this(coordinates, action);
		this.endpoints = endpoints;
	}

	public Set<String> getReservedCoordinates() {
		return reservedCoordinates;
	}

	public void setReservedCoordinates(Set<String> reservedCoordinates) {
		this.reservedCoordinates = reservedCoordinates;
	}
	
	public Set<String> getSelectableCoordinates() {
		return selectableCoordinates;
	}

	public void setSelectableCoordinates(Set<String> selectableCoordinates) {
		this.selectableCoordinates = selectableCoordinates;
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

	public String[] getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(String[] endpoints) {
		this.endpoints = endpoints;
	}

	public Set<String> getUserBookingTokens() {
		return userBookingTokens;
	}

	public void setUserBookingTokens(Set<String> userBookingTokens) {
		this.userBookingTokens = userBookingTokens;
	}

	public Set<String> getAllBookingTokens() {
		return allBookingTokens;
	}

	public void setAllBookingTokens(Set<String> allBookingTokens) {
		this.allBookingTokens = allBookingTokens;
	}
	
}