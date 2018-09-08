package com.mweka.natwende.types;

public enum ReportOutputType implements Displayable {
	
	PDF("PDF"), EXCEL("Excel"), WORD("Word");
	
	private ReportOutputType(String display) {
		this.display = display;
	}
	
	private String display;
	
	public String getDisplay() {
		return display;
	}

}
