package com.mweka.natwende.trip.entity;

import com.mweka.natwende.payment.entity.Payment;
import com.mweka.natwende.types.BookingStatus;
import com.mweka.natwende.types.OperatorName;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Booking.class)
public abstract class Booking_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Booking, Trip> trip;
	public static volatile SingularAttribute<Booking, String> customerEmail;
	public static volatile SingularAttribute<Booking, String> accountUser;
	public static volatile SingularAttribute<Booking, String> accountUserEmail;
	public static volatile SingularAttribute<Booking, BookingStatus> bookingStatus;
	public static volatile SingularAttribute<Booking, String> from;
	public static volatile SingularAttribute<Booking, Payment> payment;
	public static volatile SingularAttribute<Booking, String> to;
	public static volatile SingularAttribute<Booking, OperatorName> operatorName;
	public static volatile SingularAttribute<Booking, String> seatNumber;
	public static volatile SingularAttribute<Booking, String> customerName;

}

