package com.mweka.natwende.trip.entity;

import com.mweka.natwende.types.OperatorName;
import com.mweka.natwende.types.Town;
import com.mweka.natwende.types.TripStatus;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Trip.class)
public abstract class Trip_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Trip, Date> scheduledArrivalDate;
	public static volatile SingularAttribute<Trip, Date> actualArrivalDate;
	public static volatile SingularAttribute<Trip, Integer> availableNumOfSeats;
	public static volatile SingularAttribute<Trip, String> travelDurationActual;
	public static volatile SingularAttribute<Trip, OperatorName> operatorName;
	public static volatile SingularAttribute<Trip, Integer> bookedNumOfSeats;
	public static volatile SingularAttribute<Trip, String> routeName;
	public static volatile SingularAttribute<Trip, Date> scheduledDepartureDate;
	public static volatile SingularAttribute<Trip, String> busReg;
	public static volatile SingularAttribute<Trip, Integer> totalNumOfSeats;
	public static volatile SingularAttribute<Trip, String> travelDurationExpected;
	public static volatile SingularAttribute<Trip, Town> from;
	public static volatile SingularAttribute<Trip, String> driverName;
	public static volatile SingularAttribute<Trip, Town> to;
	public static volatile SingularAttribute<Trip, TripSchedule> tripSchedule;
	public static volatile ListAttribute<Trip, Booking> bookings;
	public static volatile SingularAttribute<Trip, Date> actualDepartureDate;
	public static volatile SingularAttribute<Trip, TripStatus> tripStatus;

}

