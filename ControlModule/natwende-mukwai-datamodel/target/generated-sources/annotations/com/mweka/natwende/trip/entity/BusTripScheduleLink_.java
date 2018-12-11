package com.mweka.natwende.trip.entity;

import com.mweka.natwende.operator.entity.Bus;
import com.mweka.natwende.user.entity.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BusTripScheduleLink.class)
public abstract class BusTripScheduleLink_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<BusTripScheduleLink, Bus> bus;
	public static volatile SingularAttribute<BusTripScheduleLink, User> driver;
	public static volatile SingularAttribute<BusTripScheduleLink, TripSchedule> tripSchedule;

}

