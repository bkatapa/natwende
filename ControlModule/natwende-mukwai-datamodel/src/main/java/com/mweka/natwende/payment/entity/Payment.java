package com.mweka.natwende.payment.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.types.PaymentOption;
import com.mweka.natwende.types.PaymentStatus;

@Entity
@Table(name = "Payment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Payment.QUERY_FIND_ALL, query=" SELECT p FROM Payment p "),
    @NamedQuery(name = Payment.QUERY_FIND_BY_REF, query=" SELECT p FROM Payment p WHERE p.ref = :ref "),
    @NamedQuery(name = Payment.QUERY_FIND_BY_NRC_AND_PAYMENT_STATUS, query=" SELECT p FROM Payment p WHERE p.customerNrc LIKE :customerNrc AND p.paymentStatus = :paymentStatus "),
    @NamedQuery(name = Payment.QUERY_FIND_BY_PHONE_AND_PAYMENT_STATUS, query=" SELECT p FROM Payment p WHERE p.phoneNumber = :phoneNumber AND p.paymentStatus = :paymentStatus ")
})
public class Payment extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7526127004975296341L;
	
	/** NAMED QUERY CONSTANTS */
	public static final transient String QUERY_FIND_ALL = "Payment.findAll";
	public static final transient String QUERY_FIND_BY_REF = "Payment.findByRef";
	public static final transient String QUERY_FIND_BY_NRC_AND_PAYMENT_STATUS = "Payment.findByNrcAndPaymentStatus";
	public static final transient String QUERY_FIND_BY_PHONE_AND_PAYMENT_STATUS = "Payment.findByPhoneAndPaymentStatus";
	
	/** QUERY PARAMETER CONSTANTS */
	public static final transient String PARAM_PAYMENT_ID = "paymentId";
	public static final transient String PARAM_REF = "ref";
	public static final transient String PARAM_PAYMENT_STATUS = "paymentStatus";
	public static final transient String PARAM_PHONE_NUMBER = "phoneNumber";
	public static final transient String PARAM_CUSTOMER_NRC = "customerNrc";

	private BigDecimal amount;
	
	@NotNull
	@Column(unique = true)
	private String ref;
	
	@NotNull
	@Column(name = "customer_name")
	private String customerName;
	
	@NotNull
	@Column(name = "customer_nrc")
	private String customerNrc;
	
	@NotNull
	private String beneficiary;
	
	@NotNull
	private String address;
	
	@NotNull
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@NotNull
	@Enumerated(EnumType.STRING)
    @Column(name = "payment_status", length = 32)
	private PaymentStatus paymentStatus;
	
	@NotNull
	@Enumerated(EnumType.STRING)
    @Column(name = "payment_option", length = 32)
	private PaymentOption paymentOption;
	
	@Column(name = "card_number", length = 20)
	private String cardNumber;

	@Column(name = "name_on_card", length = 100)
	private String nameOnCard;
	
	@Column(name = "exp_date", length = 20)
	private String expDate;
	
	@Column(name = "cvv_2", length = 20)
	private String cvv2;
	
	@NotNull
	@Column(length = 255)
	private String description;
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getRef() {
		return ref;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getBeneficiary() {
		return beneficiary;
	}
	
	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getExpDate() {
		return expDate;
	}
	
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	
	public String getCvv2() {
		return cvv2;
	}
	
	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
	
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCustomerNrc() {
		return customerNrc;
	}
	
	public void setCustomerNrc(String customerNrc) {
		this.customerNrc = customerNrc;
	}
	
	public String getNameOnCard() {
		return nameOnCard;
	}
	
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	public PaymentOption getPaymentOption() {
		return paymentOption;
	}
	public void setPaymentOption(PaymentOption paymentOption) {
		this.paymentOption = paymentOption;
	}
	
}
