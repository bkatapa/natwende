package com.mweka.natwende.payment.entity;

import com.mweka.natwende.user.entity.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Card.class)
public abstract class Card_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Card, String> expiryDate;
	public static volatile SingularAttribute<Card, User> owner;
	public static volatile SingularAttribute<Card, String> cvv2;
	public static volatile SingularAttribute<Card, String> nameOnCard;
	public static volatile SingularAttribute<Card, String> cardNumberEncrypted;
	public static volatile SingularAttribute<Card, Boolean> primary;

}

