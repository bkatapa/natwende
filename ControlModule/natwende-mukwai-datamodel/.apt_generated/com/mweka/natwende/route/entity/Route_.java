package com.mweka.natwende.route.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Route.class)
public abstract class Route_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Route, Stop> stop;
	public static volatile ListAttribute<Route, RouteStopLink> routeStops;
	public static volatile SingularAttribute<Route, String> name;
	public static volatile SingularAttribute<Route, Stop> start;
	public static volatile SingularAttribute<Route, Route> mirrorRoute;

}

