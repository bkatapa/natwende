package com.mweka.natwende.types;

public enum ActivityType implements Displayable {

	BOOKING("Booking"),
	LOGOUT("Logout"),
	LOGIN("Login");
	
	private final String display;
	
	private ActivityType(String display) {
		this.display = display;
	}
	
	@Override
	public String getDisplay() {
		return display;
	}
}
