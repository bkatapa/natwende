package com.mweka.natwende.types;

public enum Province implements Displayable {
	
	COPPERBELT("Copperbelt"),
    CENTRAL("Central"),
    LUSAKA("Lusaka"),
    SOUTHERN("Sothern"),
    EASTERN("Eastern"),
    LUAPULA("Luapula"),
    NORTHERN("Northern"),
    MUCHINGA("Muchinga"),
    WESTERN("Western"),
    NORTH_WESTERN("North-western");
	
    private final String display;

    private Province(String display) {
        this.display = display;
    }

	@Override
	public String getDisplay() {		
		return display;
	}
}
