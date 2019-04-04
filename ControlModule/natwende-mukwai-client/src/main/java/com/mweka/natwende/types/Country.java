package com.mweka.natwende.types;

public enum Country implements Displayable {

	ZAMBIA("Zambia", "Zam", "+260"),
	SOUTH_AFRICA("South Africa", "RSA", "+27");
	
	private final String display, abbrev, shortCode;
	
	private Country(String display, String abbrev, String shortCode) {
		this.display = display;
		this.abbrev = abbrev;
		this.shortCode = shortCode;
	}
	
	@Override
	public String getDisplay() {
		return display;
	}
	
	public String getAbbrev() {
		return abbrev;
	}
	
	public String getShortCode() {
		return shortCode;
	}
}