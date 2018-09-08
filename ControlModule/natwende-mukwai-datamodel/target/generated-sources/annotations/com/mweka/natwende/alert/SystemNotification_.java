package com.mweka.natwende.alert;

import com.mweka.natwende.media.entity.Media;
import com.mweka.natwende.operator.entity.Operator;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SystemNotification.class)
public abstract class SystemNotification_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<SystemNotification, String> message;
	public static volatile SingularAttribute<SystemNotification, String> subject;
	public static volatile SingularAttribute<SystemNotification, Date> notificationDate;
	public static volatile SingularAttribute<SystemNotification, Media> attachment;
	public static volatile SingularAttribute<SystemNotification, Operator> operator;

}

