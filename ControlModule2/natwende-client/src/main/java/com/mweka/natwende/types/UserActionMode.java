package com.mweka.natwende.types;

public enum UserActionMode {
	CREATE("User in create mode"),
	EDIT("User in edit mode"),
	CANCEL("User in cancel mode"),
	SINGLY_BOOKING("User in singly-booking mode"),
	BULK_BOOKING("User in bulk-booking mode"),
	REPORTS("User in booking reports mode");
	
	private UserActionMode(String mode) {
		this.mode = mode;
	}
	
	private String mode;
	
	public String getMode() {
		return mode;
	}
}
