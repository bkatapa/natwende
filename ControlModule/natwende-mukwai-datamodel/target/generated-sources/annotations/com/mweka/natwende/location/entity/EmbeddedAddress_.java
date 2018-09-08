package com.mweka.natwende.location.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmbeddedAddress.class)
public abstract class EmbeddedAddress_ {

	public static volatile SingularAttribute<EmbeddedAddress, String> postalCode;
	public static volatile SingularAttribute<EmbeddedAddress, String> name;
	public static volatile SingularAttribute<EmbeddedAddress, String> province;
	public static volatile SingularAttribute<EmbeddedAddress, String> line1;
	public static volatile SingularAttribute<EmbeddedAddress, String> country;
	public static volatile SingularAttribute<EmbeddedAddress, String> city;

}

