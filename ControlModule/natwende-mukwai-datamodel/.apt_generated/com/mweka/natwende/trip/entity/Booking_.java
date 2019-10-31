package com.mweka.natwende.trip.entity;

import com.mweka.natwende.types.BookingStatus;
import com.mweka.natwende.types.Title;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Booking.class)
public abstract class Booking_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Booking, BigDecimal> fare;
	public static volatile SingularAttribute<Booking, String> passengerNrc;
	public static volatile SingularAttribute<Booking, String> seatCoordinate;
	public static volatile SingularAttribute<Booking, String> passengerEmail;
	public static volatile SingularAttribute<Booking, String> seatNumber;
	public static volatile SingularAttribute<Booking, Trip> trip;
	public static volatile SingularAttribute<Booking, String> passengerAddress;
	public static volatile SingularAttribute<Booking, BookingStatus> bookingStatus;
	public static volatile SingularAttribute<Booking, Reservation> reservation;
	public static volatile SingularAttribute<Booking, String> from;
	public static volatile SingularAttribute<Booking, String> to;
	public static volatile SingularAttribute<Booking, String> passengerFirstName;
	public static volatile SingularAttribute<Booking, String> passengerLastName;
	public static volatile SingularAttribute<Booking, String> passengerPhoneNumber;
	public static volatile SingularAttribute<Booking, Title> passengerTitle;

}

