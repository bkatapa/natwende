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
	private FacesMessage message;
	
	public SeatUpdateVO() {			
	}
	
	public SeatUpdateVO(Set<String> coordinates, String action) {
		this.coordinates = coordinates;
		this.action = action;
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
	
}
