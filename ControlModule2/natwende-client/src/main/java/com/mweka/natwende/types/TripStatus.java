package com.mweka.natwende.types;

public enum TripStatus implements Displayable {
	
	BOOKING(1, "Booking"),
    CHECK_IN(2, "Checking in"),
    LOADING(3, "Loading"),
	STARTED(4, "Started"),
	IN_TRANSIT(5, "In transit"),
	TRANSIT_STOP(6, "Transit stop"),
	ENDED(7, "Started"),
	ABORTED(8, "Aborted"),
	UNKNOWN(9, "Unknown");
    
    int id;

    String display;

    private TripStatus(int id, String display) {
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
