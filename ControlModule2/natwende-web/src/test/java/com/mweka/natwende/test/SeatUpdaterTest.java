package com.mweka.natwende.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mweka.natwende.helper.SeatUpdater;
import com.mweka.natwende.trip.vo.ElasticTripVO;

public class SeatUpdaterTest {
	
	private static ElasticTripVO elasticTrip;

	@BeforeClass
	public static void setup() {
		elasticTrip = new ElasticTripVO();
		elasticTrip.setOccupiedSeats(Arrays.asList("1_2", "2_3", "3_4"));
		elasticTrip.setStationList(new java.util.LinkedHashSet<>(Arrays.asList("1_Lusaka", "2_Kabwe", "3_Kapiri", "4_Ndola", "5_Kitwe")));
		Set<String> userBookingTokenSet1 = new HashSet<>(Arrays.asList("3|1_2|Lusaka|Kapiri"));
		Set<String> userBookingTokenSet2 = new HashSet<>(Arrays.asList("5|3_4|Kapiri|Kitwe"));
		Set<String> userBookingTokenSet3 = new HashSet<>(Arrays.asList("4|2_3|Kabwe|Ndola"));
		Map<String, Set<String>> bookingReservationTokenMap = new HashMap<>();
		bookingReservationTokenMap.put("Anne", userBookingTokenSet1);
		bookingReservationTokenMap.put("Ben", userBookingTokenSet2);
		bookingReservationTokenMap.put("Chris", userBookingTokenSet3);
		elasticTrip.setUserBookings(bookingReservationTokenMap);
	}
	
	@Test
	public void testBookingClash() {
		String[] endpoints = new String[] {"Lusaka", "Kapiri"};
		Set<String> selectableCoordinates = Collections.singleton("1_2");
		Set<String> userBookingTokens = Collections.singleton("3|1_2|Lusaka|Kapiri");
		Set<String> reservedCoordinates = SeatUpdater.getNonSelectableCoordinatesForClient(endpoints, selectableCoordinates, userBookingTokens, elasticTrip);
		//System.out.println(reservedCoordinates);
		Assert.assertEquals(reservedCoordinates, Collections.singleton("2_3"));
	}
	
}
