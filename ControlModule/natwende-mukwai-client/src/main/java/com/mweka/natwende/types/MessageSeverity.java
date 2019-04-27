package com.mweka.natwende.types;

public enum MessageSeverity implements Displayable {
	
	INFO("info"),
	WARN("warn"),
	ERROR("error"),
	FATAL("fatal");
	
	private final String display;
	
	private MessageSeverity(String display) {
		this.display = display;
	}

	@Override
	public String getDisplay() {
		return display;
	}

}
