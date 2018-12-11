package com.mweka.natwende.route.entity;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Fare.class)
public abstract class Fare_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Fare, BigDecimal> amount;
	public static volatile SingularAttribute<Fare, Stop> from;
	public static volatile SingularAttribute<Fare, Stop> to;

}

