package com.mweka.natwende.types;

public enum PaymentOption implements Displayable {
	
	BANK_APP("Bank app"),
	CASH("Cash"),
	MOBILE_MONEY("Mobile money"),
	SWIPE_MACHINE("Swipe machine"),
	VIA_CARD("Visa card"),
	ZOONA("Zoona");

	private final String display;
	
	private PaymentOption(String display) {
		this.display = display;
	}
	
	@Override
	public String getDisplay() {
		return display;
	}

}
