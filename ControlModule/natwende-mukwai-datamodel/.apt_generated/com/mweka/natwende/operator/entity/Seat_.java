package com.mweka.natwende.operator.entity;

import com.mweka.natwende.types.SeatClass;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Seat.class)
public abstract class Seat_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Seat, Bus> bus;
	public static volatile SingularAttribute<Seat, SeatClass> seatClass;
	public static volatile SingularAttribute<Seat, String> coordinates;
	public static volatile SingularAttribute<Seat, String> seatNo;

}

