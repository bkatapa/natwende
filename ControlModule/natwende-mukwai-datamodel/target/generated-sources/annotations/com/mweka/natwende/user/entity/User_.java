package com.mweka.natwende.user.entity;

import com.mweka.natwende.location.entity.EmbeddedAddress;
import com.mweka.natwende.operator.entity.Operator;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<User, String> firstname;
	public static volatile SingularAttribute<User, EmbeddedAddress> address;
	public static volatile SingularAttribute<User, String> passwd;
	public static volatile SingularAttribute<User, byte[]> profilePic;
	public static volatile SingularAttribute<User, String> contactNumber;
	public static volatile ListAttribute<User, UserRoleLink> userRoleLinkList;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> nrc;
	public static volatile SingularAttribute<User, Operator> operator;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, String> lastname;

}

