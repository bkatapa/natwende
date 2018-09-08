package com.mweka.natwende.types;

public enum ImageStatus {
	
	IMAGE("Image uploaded"), NO_IMAGE("No Image");
	
	private ImageStatus(String display) {
		this.display = display;
	}
	private String display;
	
	public String getDisplay() {
		return display;
	}
	
}