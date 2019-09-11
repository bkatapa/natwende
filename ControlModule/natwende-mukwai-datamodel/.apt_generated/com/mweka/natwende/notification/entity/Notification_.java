package com.mweka.natwende.notification.entity;

import com.mweka.natwende.types.NotificationStatus;
import com.mweka.natwende.types.NotificationType;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Notification.class)
public abstract class Notification_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Notification, NotificationType> notitifcationType;
	public static volatile SingularAttribute<Notification, String> subject;
	public static volatile SingularAttribute<Notification, Date> submitDate;
	public static volatile SingularAttribute<Notification, Message> message;
	public static volatile SingularAttribute<Notification, NotificationStatus> notitifcationStatus;

}

