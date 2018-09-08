package com.mweka.natwende.report.entity;

import com.mweka.natwende.operator.entity.Operator;
import com.mweka.natwende.types.ReportOutputType;
import com.mweka.natwende.types.ReportType;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Report.class)
public abstract class Report_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Report, ReportType> reportType;
	public static volatile SingularAttribute<Report, String> reportFile;
	public static volatile SingularAttribute<Report, String> reportTitle;
	public static volatile SingularAttribute<Report, ReportOutputType> outputType;
	public static volatile ListAttribute<Report, ReportParameters> reportParameters;
	public static volatile SingularAttribute<Report, Operator> operator;

}

