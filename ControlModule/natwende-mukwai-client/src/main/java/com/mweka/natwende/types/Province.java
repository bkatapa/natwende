package com.mweka.natwende.types;

public enum Province implements Displayable {
	
	// Zambia
	COPPERBELT("Copperbelt", Country.ZAMBIA),
    CENTRAL("Central", Country.ZAMBIA),
    LUSAKA("Lusaka", Country.ZAMBIA),
    SOUTHERN("Sothern", Country.ZAMBIA),
    EASTERN("Eastern", Country.ZAMBIA),
    LUAPULA("Luapula", Country.ZAMBIA),
    NORTHERN("Northern", Country.ZAMBIA),
    MUCHINGA("Muchinga", Country.ZAMBIA),
    WESTERN("Western", Country.ZAMBIA),
    NORTH_WESTERN("North-western", Country.ZAMBIA),
	    
    // South Africa
	GAUTENG("Gauteng", Country.SOUTH_AFRICA),
	WESTERN_CAPE("Western cape", Country.SOUTH_AFRICA),
	KWAZULU_NATAL("Kwazulu Natal", Country.SOUTH_AFRICA),
	EASTERN_CAPE("Eastern cape", Country.SOUTH_AFRICA);
	
    private final String display;
    private final Country country;

    private Province(String display, Country country) {
        this.display = display;
        this.country = country;
    }

	@Override
	public String getDisplay() {		
		return display;
	}
	
	public Country getCountry() {
		return country;
	}
}
