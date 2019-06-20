package com.mweka.natwende.trip.entity;

import com.mweka.natwende.payment.entity.Payment;
import com.mweka.natwende.types.BookingStatus;
import com.mweka.natwende.user.entity.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Reservation.class)
public abstract class Reservation_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Reservation, BookingStatus> bookingStatus;
	public static volatile SingularAttribute<Reservation, Payment> payment;
	public static volatile SingularAttribute<Reservation, User> customer;

}

