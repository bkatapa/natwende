package com.mweka.natwende.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import com.mweka.natwende.route.vo.StretchVO;
import com.mweka.natwende.route.vo.StopVO;

public class StretchHelper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1735094125219150052L;
	
	public List<StretchVO> computeAllTravelTimePermutations(List<StretchVO> stretchList, List<StretchVO> allStretchList) throws Exception {
		List<StretchVO> resultList = new ArrayList<>();
		
		for (StretchVO outer : allStretchList) {
			if (outer.getId() > -1L) {
				resultList.add(outer);
				continue;
			}
			if (outer.getEstimatedTravelTime() == null) {
				outer.setEstimatedTravelTime(getZeroDate());
			}
			StopVO outerFrom = outer.getFrom();
			StopVO outerTo = outer.getTo();
			StopVO innerFrom;
			StopVO innerTo;
			Calendar cOuter = Calendar.getInstance();
			Calendar cInner = Calendar.getInstance();
			
			//System.out.println("Outer => " + outer + ", outer.getEstimatedTravelTime() => " + outer.getEstimatedTravelTime());
			
			cOuter.setTime(outer.getEstimatedTravelTime());
			
			for (StretchVO inner : stretchList) {
				innerFrom = inner.getFrom();
				innerTo = inner.getTo();
				cInner.setTime(inner.getEstimatedTravelTime());
				
				if (outerFrom.equals(innerFrom)) {
					if (cInner.get(Calendar.HOUR) == 0 && cInner.get(Calendar.MINUTE) == 0) {
						throw new Exception("Estimated travel time for stretch was empty: [" + innerFrom + " - " + innerTo + "]");
					}
					outer.setEstimatedTravelTime(DateUtils.addHours(outer.getEstimatedTravelTime(), cInner.get(Calendar.HOUR)));
					outer.setEstimatedTravelTime(DateUtils.addMinutes(outer.getEstimatedTravelTime(), cInner.get(Calendar.MINUTE)));
					
					if (outerTo.equals(innerTo)) { // Closure achieved.
						resultList.add(outer);
						break; // Inner loop.
					}
					outerFrom = innerTo;
				}
			}			
		}
		return resultList;
	}
	
	public static Date getZeroDate() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
}
