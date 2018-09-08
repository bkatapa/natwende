package com.mweka.natwende.types;

public enum ReportParameterType implements Displayable {
	USER("User");	
	
	
	private String display;
	
	private ReportParameterType(String display) {
		this.display = display;
	}

	@Override
	public String getDisplay() {
		return display;
	}

}
