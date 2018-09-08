package com.mweka.natwende.util;

import java.util.Comparator;

import com.mweka.natwende.route.vo.RouteStopLinkVO;

public class ReverseTransitComparator implements Comparator<RouteStopLinkVO> {

	@Override
	public int compare(RouteStopLinkVO link1, RouteStopLinkVO link2) {
		if (link1.getStationIndex() > link2.getStationIndex()) {
			return -1;
		}
		if (link1.getStationIndex() < link2.getStationIndex()) {
			return 1;
		}
		return 0;
	}
	
}
