package com.mweka.natwende.util;

import java.text.DecimalFormat;

public class NumberFormatter {
	
	public static String formatNumberToTwoDecimal(Double value){
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(value);
	}
}
