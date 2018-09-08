package com.mweka.natwende.mis;

import java.io.Serializable;
import java.util.HashMap;

public class DynamicChartObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private HashMap<String, String> dataStore;
   
   public DynamicChartObject() {
	   dataStore = new HashMap<String, String>(); 
   }
   
   public String getValue(ChartField key) {
	   String value = dataStore.get(key.name());
	   if (value == null) {
		   return "";
	   } else {
		   return value;
	   }	   
   }
   
   public void setValue(ChartField key, String value) {
	   dataStore.put(key.name(), value);
   }
   
   public void setValue(ChartField key, Long value) {
	   dataStore.put(key.name(), value + "");
   }
}
