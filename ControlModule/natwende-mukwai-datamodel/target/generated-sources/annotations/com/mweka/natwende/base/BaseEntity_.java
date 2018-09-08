package com.mweka.natwende.base;

import com.mweka.natwende.types.Status;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BaseEntity.class)
public abstract class BaseEntity_ {

	public static volatile SingularAttribute<BaseEntity, Long> id;
	public static volatile SingularAttribute<BaseEntity, Status> status;
	public static volatile SingularAttribute<BaseEntity, Date> insertDate;
	public static volatile SingularAttribute<BaseEntity, Date> updateDate;
	public static volatile SingularAttribute<BaseEntity, Long> version;
	public static volatile SingularAttribute<BaseEntity, String> uniqueId;

}

