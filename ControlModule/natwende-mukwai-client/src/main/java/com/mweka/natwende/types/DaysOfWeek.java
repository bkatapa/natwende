package com.mweka.natwende.types;

public enum DaysOfWeek {

	SUNDAY("Sunday"),
	MONDAY("Monday"),
	TUESDAY("Tuesday"),
	WEDNESDAY("Wednesday"),
	THURSDAY("Thursday"),
	FRIDAY("Friday"),
	SATURDAY("Saturday");
	
	private final String display;
	
	private DaysOfWeek(String display) {
		this.display = display;
	}
	
	public String getDisplay() {
		return display;
	}
}
