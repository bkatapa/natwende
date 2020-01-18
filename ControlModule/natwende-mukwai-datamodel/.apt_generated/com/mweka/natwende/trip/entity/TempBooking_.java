package com.mweka.natwende.trip.entity;

import com.mweka.natwende.types.SeatStatus;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TempBooking.class)
public abstract class TempBooking_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<TempBooking, Date> lastHeartBeat;
	public static volatile SingularAttribute<TempBooking, SeatStatus> seatStatus;
	public static volatile SingularAttribute<TempBooking, Long> tripId;
	public static volatile SingularAttribute<TempBooking, String> wsSessionId;
	public static volatile SingularAttribute<TempBooking, String> seatNo;

}

