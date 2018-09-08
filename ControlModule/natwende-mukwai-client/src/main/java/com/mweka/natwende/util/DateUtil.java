package com.mweka.natwende.util;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {
	public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String YEAR_MONTH_FORMAT = "yyyy-MM";
	public static final String SHORT_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";
	public static final String SHORT_DATE_FORMAT = "dd/MM/yyyy";
	public static final String SHORT_TIME_FORMAT = "HH:mm";

	public static Date addDaysTodDate(Date dateToUse, int days) {

		if (dateToUse == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToUse);
		calendar.add(Calendar.DATE, days);

		return calendar.getTime();
	}

	public static Date truncDateAndAdd(Date dateToUse, int days) {
		if (dateToUse == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToUse);

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, days);

		return calendar.getTime();
	}

	public static Date padDateWithLastTimeOfTheDay(Date dateToUse) {

		if (dateToUse == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToUse);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getMaximum(Calendar.MILLISECOND));

		return calendar.getTime();
	}

	public static Date padDateWithLastDayOfTheMonth(Date dateToUse) {

		if (dateToUse == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToUse);
		calendar.set(Calendar.DATE, calendar.getMaximum(Calendar.DATE));

		return calendar.getTime();
	}

	public static Date padDateWithFirstDayOfTheMonth(Date dateToUse) {

		if (dateToUse == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToUse);
		calendar.set(Calendar.DATE, calendar.getMinimum(Calendar.DATE));

		return calendar.getTime();
	}

	public static Date getDateNext6MonthsIncludingCurrent() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, 5);
		return padDateWithLastDayOfTheMonth(calendar.getTime());
	}

	public static String getDateFormat() {
		return SHORT_DATE_FORMAT;
	}

	public static String getShortDateTime() {
		return SHORT_DATE_TIME_FORMAT;
	}

	public static String getCurrency(String code) {
		return "R ";
	}

	/**
	 * Convert a millisecond duration to a string format
	 * 
	 * @param millis
	 *            A duration to convert to a string form
	 * @return A string of the form "X Days Y Hours Z Minutes A Seconds".
	 */
	public static String getDurationBreakdown(long millis) {
		if (millis < 0) {
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

		return (sb.toString());
	}
}
