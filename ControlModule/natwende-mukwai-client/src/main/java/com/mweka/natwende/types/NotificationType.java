package com.mweka.natwende.types;

public enum NotificationType implements Displayable {
	
	SEAT("Seat"),
	PAYMENT("Payment");
	
	private final String display;
	
	private NotificationType(String display) {
		this.display = display;
	}

	@Override
	public String getDisplay() {
		return display;
	}

}
