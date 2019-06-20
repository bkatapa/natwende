package com.mweka.natwende.notification.entity;

import com.mweka.natwende.types.MessageSeverity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Message.class)
public abstract class Message_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Message, MessageSeverity> severity;
	public static volatile SingularAttribute<Message, String> summary;
	public static volatile SingularAttribute<Message, String> detail;

}

