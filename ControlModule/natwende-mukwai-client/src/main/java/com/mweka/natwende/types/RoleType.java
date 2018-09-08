/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.types;

public enum RoleType {

    SYSTEM_ADMINISTRATOR("System Administrator"),
    NATWENDE_ADMINISTRATOR("Natwende Administrator"),
    NATWENDE_OPERATOR("Natwende Operator"),
    BUS_ADMINISTRATOR("Bus Administrator"),
    BUS_OPERATOR("Bus Operator"),
    BUS_DRIVER("Bus Driver"),
    BUS_HOST("Bus Host"),
    PASSENGER("Passenger"),
    BUS_USER("Bus User");

    private final String description;

    private RoleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return name();
    }
}
