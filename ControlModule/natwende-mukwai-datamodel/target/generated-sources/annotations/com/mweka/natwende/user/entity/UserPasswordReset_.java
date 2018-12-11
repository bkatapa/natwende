package com.mweka.natwende.user.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserPasswordReset.class)
public abstract class UserPasswordReset_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<UserPasswordReset, Date> expiryDate;
	public static volatile SingularAttribute<UserPasswordReset, String> resetPin;
	public static volatile SingularAttribute<UserPasswordReset, User> user;
	public static volatile SingularAttribute<UserPasswordReset, String> email;

}

