package com.mweka.natwende.location.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

import com.mweka.natwende.types.Town;

@Embeddable
public class EmbeddedAddress implements Serializable {
 	private static final long serialVersionUID = 1L;
 	
	@Size(max = 45)
    @Column(length = 45)
    private String premises;
	
    @Size(max = 255)
    @Column(length = 255)
    private String line1;
    
    @Size(max = 255)
    @Column(length = 255)
    private String street;
    
    @Size(max = 255)
    @Column(length = 255)
    private String surbab;
    
    @Enumerated(EnumType.STRING)
    private Town town;
    
	public String getPremises() {
		return premises;
	}
	
	public void setPremises(String premises) {
		this.premises = premises;
	}
	
	public String getLine1() {
		return line1;
	}
	
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSurbab() {
		return surbab;
	}

	public void setSurbab(String surbab) {
		this.surbab = surbab;
	}

	public Town getTown() {
		return town;
	}
	
	public void setTown(Town town) {
		this.town = town;
	}
	
}
