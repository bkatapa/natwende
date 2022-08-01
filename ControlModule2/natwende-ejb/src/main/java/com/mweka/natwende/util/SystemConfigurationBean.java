package com.mweka.natwende.util;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.commons.logging.Log;

@Startup
@Singleton
public class SystemConfigurationBean {
	
	private boolean sendWelcomeEmails = false;
	private String baseURL;
	private String reportEnginePath;
	private String parkingDatabaseHost;
	private String parkingDatabaseName;
	private String parkingDatabaseUsername;
	private String parkingDatabasePassword;
	private String helpURL;
	
	@Inject
	private Log log;
	
	@PostConstruct
	public void configure() {
		sendWelcomeEmails = Boolean.parseBoolean(System.getProperty("skidata-send-welcome-emails", "true"));
		baseURL = System.getProperty("skidata-base-url", "http://localhost:8080/skidata-parking");
		reportEnginePath = System.getProperty("skidata-report-engine-path", "/opt/reports/skidata-parking/");
		parkingDatabaseHost = System.getProperty("skidata-parking-database-host", "localhost");
		parkingDatabaseName = System.getProperty("skidata-parking-database-name", "skidata-parking");
		parkingDatabaseUsername = System.getProperty("skidata-parking-database-username", "skidata");
		parkingDatabasePassword = System.getProperty("skidata-parking-database-password", "SK!d@ta7865657h");
		helpURL = System.getProperty("skidata-help-url", "http://www01.za.adaptris.net/help.pdf");
		log.info(String.format("####### WM BUYER CONFIG #######: sendWelcomeEmails(%b), baseURL(%s), reportEnginePath(%s)", sendWelcomeEmails, baseURL, reportEnginePath));		
	}

	public boolean mustSendWelcomeEmails() {
		return sendWelcomeEmails;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public String getReportEnginePath() {
		return reportEnginePath;
	}

	public String getHelpURL() {
	    return helpURL;
	}

	public String getParkingDatabaseHost() {
		return parkingDatabaseHost;
	}

	public String getParkingDatabaseName() {
		return parkingDatabaseName;
	}

	public String getParkingDatabaseUsername() {
		return parkingDatabaseUsername;
	}

	public String getParkingDatabasePassword() {
		return parkingDatabasePassword;
	}
	
}
