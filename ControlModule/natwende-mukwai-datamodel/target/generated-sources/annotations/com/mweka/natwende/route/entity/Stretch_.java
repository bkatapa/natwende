package com.mweka.natwende.route.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Stretch.class)
public abstract class Stretch_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Stretch, Date> observedTravelTime;
	public static volatile SingularAttribute<Stretch, Date> estimatedTravelTime;
	public static volatile SingularAttribute<Stretch, BigDecimal> distanceKM;
	public static volatile SingularAttribute<Stretch, Stop> from;
	public static volatile SingularAttribute<Stretch, Stop> to;
	public static volatile SingularAttribute<Stretch, BigDecimal> fareAmount;

}

