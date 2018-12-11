package com.mweka.natwende.route.entity;

import com.mweka.natwende.types.Province;
import com.mweka.natwende.types.Town;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Stop.class)
public abstract class Stop_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Stop, Town> town;
	public static volatile SingularAttribute<Stop, Province> province;
	public static volatile ListAttribute<Stop, RouteStopLink> routeStops;
	public static volatile SingularAttribute<Stop, String> name;

}

