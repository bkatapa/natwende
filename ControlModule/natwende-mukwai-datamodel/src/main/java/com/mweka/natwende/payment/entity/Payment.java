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
import com.mweka.natwende.types.PaymentStatus;

@Entity
@Table(name = "Payment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query="SELECT p FROM Payment p"),    
})
public class Payment extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7526127004975296341L;

	private BigDecimal amount;
	
	@NotNull
	private String ref;
	
	@NotNull
	private String customerName;
	
	@NotNull
	private String beneficiary;
	
	@NotNull
	private String address;
	
	@NotNull
	private String phoneNumber;
	
	@NotNull
	@Enumerated(EnumType.STRING)
    @Column(length = 32)
	private PaymentStatus paymentStatus;	
	
	@NotNull
	@Column(length = 20)
	private String cardNumber;
	
	@NotNull
	@Column(length = 20)
	private String expDate;
	
	@NotNull
	@Column(length = 20)
	private String cvv2;
	
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
	
}
