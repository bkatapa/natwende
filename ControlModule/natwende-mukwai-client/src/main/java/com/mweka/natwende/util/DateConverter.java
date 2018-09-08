package com.mweka.natwende.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    public static final String FULL_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public static final String FULL_DATE_FORMAT = "yyyy/MM/dd";
    public static final String FULL_TIME_FORMAT = "HH:mm:ss";
    public static final String FULL_DATE_TIME_FORMAT_WITH_NO_SECONDS = "yyyy/MM/dd HH:mm";
    public static final String SHORT_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
    public static final String SHORT_DATE_FORMAT = "yyyy/MM/dd";
    public static final String SHORT_TIME_FORMAT = "HH:mm";
    
    public static String getDateFormatPattern() {
		return FULL_DATE_FORMAT;
	}

	public static DateFormat getDateFormat() {
		return new SimpleDateFormat(getDateFormatPattern());		
	}
	
	public static String getDateTimeFormatPattern() {
		return FULL_DATE_TIME_FORMAT;
	}	
	
	public static DateFormat getDateTimeFormat() {	
		return new SimpleDateFormat(getDateTimeFormatPattern());
	}
	
    public static String convertDateToString(Date date) {
        String stringDate = "";
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(FULL_DATE_FORMAT);
            stringDate = sdf.format(date);
        }
        return stringDate;
    }
    
    public static String convertDateTimeToString(Date date) {
        String stringDate = "";
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(FULL_DATE_TIME_FORMAT);
            stringDate = sdf.format(date);
        }
        return stringDate;
    }

	
    
}
