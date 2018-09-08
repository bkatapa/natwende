package com.mweka.natwende.types;

public enum SeatClass implements Displayable {
	
	STANDARD("Standard"),
    ECONOMY("Economy"),
    BUSINESS("Business");
	
    private final String display;

    private SeatClass(String display) {
        this.display = display;
    }

	@Override
	public String getDisplay() {		
		return display;
	}
}
