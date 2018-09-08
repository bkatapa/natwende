package com.mweka.natwende.types;

public enum BookingStatus implements Displayable {

	PENDING(1, "Pending"),
    CONFIRMED(2, "Confirmed"),
    CANCELLED(3, "Cancelled"),	
	UNKNOWN(4, "Unknown");
    
    int id;

    String display;

    private BookingStatus(int id, String display) {
        this.id = id;
        this.display = display;
    }

    public int getId() {
        return id;
    }

    public String getDisplay() {
        return display;
    }
	
}
