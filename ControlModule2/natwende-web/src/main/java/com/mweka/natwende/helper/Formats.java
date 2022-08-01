package com.mweka.natwende.helper;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;

@Named
public class Formats implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	public  final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public  final String DATE_FORMAT = "dd/MM/yyyy";
    public  final String TIME_FORMAT = "HH:mm:ss";
    public  final String YEAR_MONTH_FORMAT = "yyyy-MM";
    public  final String SHORT_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";
    public  final String SHORT_DATE_FORMAT = "dd/MM/yyyy";
    public  final String SHORT_TIME_FORMAT = "HH:mm";
    
    public String getDateFormat() {
    	return SHORT_DATE_FORMAT; 
    }
    
    public String getShortDateTime() {
    	return SHORT_DATE_TIME_FORMAT;
    }
    
    
    public String getCurrency(String code) {
    	return "R ";
    }
    
    /**
     * Convert a millisecond duration to a string format
     * 
     * @param millis A duration to convert to a string form
     * @return A string of the form "X Days Y Hours Z Minutes A Seconds".
     */
    public static String getDurationBreakdown(long millis) {
        if(millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append(" Days ");
        sb.append(hours);
        sb.append(" Hours ");
        sb.append(minutes);
        sb.append(" Minutes ");
        sb.append(seconds);
        sb.append(" Seconds");

        return(sb.toString());
    }
}
