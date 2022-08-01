package com.mweka.natwende.resource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.mweka.natwende.resource.operator.BusResource;
import com.mweka.natwende.resource.payment.PaymentResource;
import com.mweka.natwende.resource.trip.BookingResource;
import com.mweka.natwende.resource.trip.ReservationResource;
import com.mweka.natwende.resource.trip.TripResource;
import com.mweka.natwende.resource.user.UserResource;

@ApplicationPath("/services")
public class NatwendeRestApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<>();
		s.add(PaymentResource.class);
		s.add(TripResource.class);
		s.add(BookingResource.class);
		s.add(ReservationResource.class);
		s.add(BusResource.class);
		s.add(UserResource.class);
		return s;
	}
	
}
