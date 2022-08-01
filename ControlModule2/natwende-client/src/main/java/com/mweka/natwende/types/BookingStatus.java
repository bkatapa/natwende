package com.mweka.natwende.types;

public enum BookingStatus implements Displayable {

	RESERVED(1, "Reserved, pending payment"),
    CONFIRMED(2, "Confirmed fully paid"),
    CANCELLED(3, "Cancelled"),
    FAILED(3, "Failed"),
	UNKNOWN(4, "Unknown");
    
    private final int id;

    private final String display;

    private BookingStatus(int id, String display) {
        this.id = id;
        this.display = display;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getDisplay() {
        return display;
    }
	
}
