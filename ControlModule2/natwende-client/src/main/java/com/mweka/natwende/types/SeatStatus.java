package com.mweka.natwende.types;

public enum SeatStatus implements Displayable {

    OCCUPIED(1, "Occupied"),
    VACANT(2, "Vacant");
    
    int id;

    String display;

    private SeatStatus(int id, String display) {
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