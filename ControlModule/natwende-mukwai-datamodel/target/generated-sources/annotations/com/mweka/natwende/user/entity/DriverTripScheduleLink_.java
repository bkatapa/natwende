package com.mweka.natwende.user.entity;

import com.mweka.natwende.trip.entity.TripSchedule;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DriverTripScheduleLink.class)
public abstract class DriverTripScheduleLink_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<DriverTripScheduleLink, TripSchedule> tripSchedule;
	public static volatile SingularAttribute<DriverTripScheduleLink, User> driver;

}

