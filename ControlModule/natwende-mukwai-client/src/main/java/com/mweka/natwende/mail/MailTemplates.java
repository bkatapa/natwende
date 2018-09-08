package com.mweka.natwende.mail;

public enum MailTemplates {
	USER_WELCOME_EMAIL("Location of user welcome email template", "templates/welcome_email.vm"),
	USER_RESET_PASSWORD_EMAIL("Location of user reset password email template", "templates/reset_pass.vm"),
	BOOKING_VOUCHER_EMAIL("Location of the booking voucher email template", "templates/booking_voucher.vm");
	
	private String description, location;
	
	private MailTemplates(String description, String location) {
		this.description = description;
		this.location = location;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getLocation() {
		return location;
	}
}
