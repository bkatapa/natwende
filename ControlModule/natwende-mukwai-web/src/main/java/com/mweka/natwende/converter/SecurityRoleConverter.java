package com.mweka.natwende.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.mweka.natwende.types.RoleType;

@FacesConverter(value="SecurityRoleConverter")
public class SecurityRoleConverter extends EnumConverter {

    public SecurityRoleConverter() {
        super(RoleType.class);
    }

}
