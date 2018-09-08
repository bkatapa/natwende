package com.mweka.natwende.media.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Media.class)
public abstract class Media_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Media, String> fileName;
	public static volatile SingularAttribute<Media, byte[]> data;
	public static volatile SingularAttribute<Media, Long> length;
	public static volatile SingularAttribute<Media, String> mime;

}

