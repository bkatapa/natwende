package com.mweka.natwende.types;

public enum ConfigAttribute {	
	OPERATOR_NAME("Parameter Name", "Simple name of Parking operator"),
	EMAIL_SUPPORT("Parameter Email", "Email address for support staff"),
	OPERATOR_WEBSITE("Parameter Url", "Fully qualified protocol name of parking operator website address"),
	OPERATOR_WEBSITE_NAME("Parameter Name", "Website address of Parking operator"),
	OPERATOR_LOGO("Parameter image", "Location of parking operator logo"),
	BANNER_COLOR("Parameter Css", "Color of top/banner section of tenant template"),
	BANNER_BGCOLOR("Parameter Css", "Background color of top/banner section of tenant template"),
	BODY_COLOR("Parameter Css", "Color of the main/body section of tenant template"),
	BODY_BGCOLOR("Parameter Css", "Background color of main/body section of tenant template"),
	FOOTER_COLOR("Parameter Css", "Color of bottom/footer section of tenant template"),
	FOOTER_BGCOLOR("Parameter Css", "Background color of bottom/footer section of tenant template"),
	PHONE_NUMBER_SUPPORT("Parameter Name", "Parking operator helpline/support telephone number"),
	PRIMARY_COLOR_CODE("Css color code", "Parking operator primary css color code"),
	SECONDARY_COLOR_CODE("Css color code", "Parking operator secondary css color code"),
	TERTIARY_COLOR_CODE("Css color code", "Parking operator tertary css color code"),
	FOOTER_COLOR_CODE("Css color code", "Parking operator footer section css color code"),
	OPERATOR_CATCH_PHRASE("Parameter Name", "Parking operator summary/catch phrase"),
	INSTRUCTIONS_ABOVE_QRCODE("Parameter Name", "Parking instructions immediately above QRCode"),
	INSTRUCTIONS_BELOW_QRCODE("Parameter Name", "Parking instructions immediately below QRCode");
		
	private String category, description;
	
	private ConfigAttribute(String category, String description) {
		this.category = category;
		this.description = description;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getDescription() {
		return description;
	}
}
