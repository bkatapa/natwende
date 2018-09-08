package com.mweka.natwende.types;

public enum NotificationStatus {
	SAVED("Saved"),
	SUBMITTED("Submitted");
		
	private NotificationStatus(String display) {
		this.display = display;
	}
	
	private String display;
	
	public String getDisplay() {
		return display;
	}

}
