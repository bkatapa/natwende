package com.mweka.natwende.util;

public class NullHelper {
	
    public static String getNonNullStringValue(Object value){
    	return (value != null?value.toString():"");
    }
    
    public static String getNonNullStringNumberValue(Object value){
    	return (value != null?value.toString():"0");
    }

}
