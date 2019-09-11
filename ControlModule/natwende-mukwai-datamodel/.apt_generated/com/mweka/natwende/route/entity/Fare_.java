package com.mweka.natwende.route.entity;

import com.mweka.natwende.operator.entity.Operator;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Fare.class)
public abstract class Fare_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Fare, Stretch> stretch;
	public static volatile SingularAttribute<Fare, BigDecimal> amount;
	public static volatile SingularAttribute<Fare, Operator> operator;

}

