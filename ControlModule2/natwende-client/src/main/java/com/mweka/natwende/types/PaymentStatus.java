package com.mweka.natwende.types;

public enum PaymentStatus implements Displayable {
	
	AUTHORIZED("Authorized"),
    REVERSED("Reversed"),
    PENDING("Pending"),
    FAILED("Failed"),
    UNKNOWN("Unknown"),
    ABORTED("Aborted"),
    DECLINED("Declined");
	
    private final String display;

    private PaymentStatus(String display) {
        this.display = display;
    }

	@Override
	public String getDisplay() {		
		return display;
	}

}
