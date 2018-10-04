package com.mweka.natwende.trip.entity;

import com.mweka.natwende.operator.entity.Operator;
import com.mweka.natwende.route.entity.Route;
import com.mweka.natwende.types.DaysOfWeek;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TripSchedule.class)
public abstract class TripSchedule_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile ListAttribute<TripSchedule, BusTripScheduleLink> busTripScheduleLinkList;
	public static volatile SingularAttribute<TripSchedule, Date> startDate;
	public static volatile SingularAttribute<TripSchedule, Route> route;
	public static volatile SingularAttribute<TripSchedule, Date> endDate;
	public static volatile ListAttribute<TripSchedule, DaysOfWeek> frequency;
	public static volatile SingularAttribute<TripSchedule, Operator> operator;
	public static volatile SingularAttribute<TripSchedule, Date> scheduledDepartureTime;

}

