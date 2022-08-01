package com.mweka.natwende.types;

public enum ReportType {
	
	ACTIVE_BOOKINGS("activebookings.jrxml", "Active Bookings Report"), 
	CANCELLED_BOOKINGS("cancelledbookings.jrxml", "Cancelled Bookings Report"),
	PARKING_BOOKINGS("parkingbookings.jrxml", "Parking Bookings Report"), 
	BOOKING_VOUCHER("bookingvoucher.jrxml", "Booking Voucher");
		
	private ReportType(String reportFileName, String displayName) {
		this.reportFileName = reportFileName;	
		this.displayName = displayName;	
	}
	
	private String reportFileName;
	private String displayName;

	public String getReportFileName() {
		return reportFileName;
	}

	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getName(){
		return name();
	}

}
