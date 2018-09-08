package com.mweka.natwende.user.entity;

import com.mweka.natwende.types.RoleType;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Role, RoleType> roleType;
	public static volatile ListAttribute<Role, UserRoleLink> userRoleLinkList;

}

