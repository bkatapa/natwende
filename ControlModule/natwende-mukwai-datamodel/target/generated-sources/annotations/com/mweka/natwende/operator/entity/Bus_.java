package com.mweka.natwende.operator.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Bus.class)
public abstract class Bus_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Bus, String> imgUrl;
	public static volatile SingularAttribute<Bus, String> seatsAsString;
	public static volatile SingularAttribute<Bus, String> reg;
	public static volatile ListAttribute<Bus, Seat> seats;
	public static volatile SingularAttribute<Bus, Operator> operator;
	public static volatile SingularAttribute<Bus, Integer> capacity;

}

