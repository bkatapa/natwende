package com.mweka.natwende.payment.entity;

import com.mweka.natwende.types.PaymentStatus;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Payment.class)
public abstract class Payment_ extends com.mweka.natwende.base.BaseEntity_ {

	public static volatile SingularAttribute<Payment, String> cvv2;
	public static volatile SingularAttribute<Payment, BigDecimal> amount;
	public static volatile SingularAttribute<Payment, String> ref;
	public static volatile SingularAttribute<Payment, String> address;
	public static volatile SingularAttribute<Payment, String> phoneNumber;
	public static volatile SingularAttribute<Payment, String> beneficiary;
	public static volatile SingularAttribute<Payment, String> customerName;
	public static volatile SingularAttribute<Payment, PaymentStatus> paymentStatus;
	public static volatile SingularAttribute<Payment, String> cardNumber;
	public static volatile SingularAttribute<Payment, String> expDate;

}

