package com.mweka.natwende.log.entity;

import com.mweka.natwende.types.ActivityType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ActivityLog.class)
public abstract class ActivityLog_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<ActivityLog, String> data;
	public static volatile SingularAttribute<ActivityLog, String> userSessionId;
	public static volatile SingularAttribute<ActivityLog, ActivityType> activityType;
	public static volatile SingularAttribute<ActivityLog, String> username;

}

