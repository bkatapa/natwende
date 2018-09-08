package com.mweka.natwende.types;

public enum OperatorName implements Displayable {
	
	POST_BUS("Post Bus", "/bus/post-bus.png"),
    MAZHANDU("Mazhandu", "/bus/mazhandu-bus.png"),
    JULDAN("Juldan", "/bus/juldan-bus.png"),
    SHALOM("Shalom", "/bus/shalom-bus.png"),
    POWER_TOOLS("Power Tools", "/bus/powertools-bus.png"),
    EURO("Euro Africa Bus Services", "/bus/euro-bus.png"),
    WADA_CHOVU("Wada Chovu", "/bus/wadachovu-bus.png");
	
    private final String display;
    private final String url;

    private OperatorName(String display, String url) {
        this.display = display;
        this.url = url;
    }

	@Override
	public String getDisplay() {		
		return display;
	}
	
	public String getUrl() {		
		return url;
	}
}
