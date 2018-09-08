package com.mweka.natwende.types;

public enum Status implements Displayable {

    ACTIVE(1, "Active"),
    INACTIVE(2, "Inactive"),
    DELETED(3, "Deleted"),
	CANCELLED(4, "Cancelled");
    
    int id;

    String display;

    private Status(int id, String display) {
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
