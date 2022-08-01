package com.mweka.natwende.types;

public enum Title implements Displayable  {

	MR("Mr."),
	MRS("Mrs."),
	MISS("Miss."),
	MS("Ms."),
	DR("Dr."),
	PST("Pst."),
	PROF("Prof."),
	REV("Rev."),
	BIS("Bis.");
	
	private final String display;
	
	private Title(String display) {
		this.display = display;
	}
	
	@Override
	public String getDisplay() {
		return display;
	}
}
