package com.mweka.natwende.mis;

public enum ChartField {

	MESSAGE_STATUS("Message Status"), MESSAGE_DATE("Message Date"), MESSAGE_COUNT("Message Count");

	String display;

	private ChartField(String display) {

		this.display = display;
	}

	public String getDisplay() {
		return display;
	}

}
