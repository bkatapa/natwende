package com.mweka.natwende.types;

public enum AddressType {

    POSTAL(1, "Postal"),
    DELIVERY(2, "Delivery");
    
    int id;

    String display;

    private AddressType(int id, String display) {
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
