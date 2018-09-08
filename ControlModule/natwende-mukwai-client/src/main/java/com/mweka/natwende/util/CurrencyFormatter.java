package com.mweka.natwende.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 
 * @author theleler
 *
 */
public class CurrencyFormatter {
	
	public static String formatNumberToCurrency(String currencySymbol, BigDecimal value){
		if (value == null) return "";
		if (value instanceof BigDecimal) {
                    DecimalFormat df = new DecimalFormat("#,##0.00");
                    return currencySymbol + " " + df.format(value);			
		} else {
                    return "";
                }
	}
}
