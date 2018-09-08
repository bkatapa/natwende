package com.mweka.natwende.location.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
public class EmbeddedAddress implements Serializable {
 	private static final long serialVersionUID = 1L;
	@Size(max = 45)
    @Column(length = 45)
    private String name;
    @Size(max = 255)
    @Column(length = 255)
    private String line1;
    @Size(max = 255)
    @Column(length = 255)
    private String city;
    @Size(max = 255)
    @Column(length = 255)
    private String province;
    @Size(max = 255)
    @Column(length = 255)
    private String postalCode;
    @Size(max = 255)
    @Column(length = 255)
    private String country;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
