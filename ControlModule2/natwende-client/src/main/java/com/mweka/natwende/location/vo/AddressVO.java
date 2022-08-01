package com.mweka.natwende.location.vo;

import com.mweka.natwende.base.vo.BaseVO;

public class AddressVO extends BaseVO {

	private static final long serialVersionUID = 1L;
	private String name;
	private String line1;
	private String street;
	private String surbub;
	private String city;
	private String province;
	private String postalCode;
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

	public String toDisplayString() {
		return line1 + (city == null ? "" : ", " + city) + (province == null ? "" : ", " + province) + ", "
				+ (postalCode == null ? "" : postalCode) + (country == null ? "" : ", " + country);
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSurbub() {
		return surbub;
	}

	public void setSurbub(String surbub) {
		this.surbub = surbub;
	}

	@Override
	public String toString() {
		return name + " , " + city;
	}

}
