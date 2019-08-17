package com.mweka.natwende.location.entity;

import com.mweka.natwende.types.Town;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmbeddedAddress.class)
public abstract class EmbeddedAddress_ {

	public static volatile SingularAttribute<EmbeddedAddress, String> surbab;
	public static volatile SingularAttribute<EmbeddedAddress, Town> town;
	public static volatile SingularAttribute<EmbeddedAddress, String> premises;
	public static volatile SingularAttribute<EmbeddedAddress, String> street;
	public static volatile SingularAttribute<EmbeddedAddress, String> line1;

}

