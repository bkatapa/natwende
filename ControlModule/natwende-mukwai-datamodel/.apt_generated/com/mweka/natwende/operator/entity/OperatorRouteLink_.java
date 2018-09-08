package com.mweka.natwende.operator.entity;

import com.mweka.natwende.route.entity.Route;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OperatorRouteLink.class)
public abstract class OperatorRouteLink_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<OperatorRouteLink, Route> route;
	public static volatile SingularAttribute<OperatorRouteLink, Operator> operator;

}

