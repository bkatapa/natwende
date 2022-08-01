package com.mweka.natwende.payment.vo;

import java.math.BigDecimal;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.types.PaymentOption;
import com.mweka.natwende.types.PaymentStatus;

@XmlRootElement(name = "payment")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5452216082042385041L;
	
	private BigDecimal amount;
	private String cardNumber;
	private String nameOnCard;
	private String expDate;
	private String cvv2;
	private String ref;
	private String customerName;
	private String customerNrc;
	private String beneficiary;
	private String address;
	private String phoneNumber;
	private PaymentStatus paymentStatus;
	private PaymentOption paymentOption;
	private String description;
	
	public PaymentVO() {
		String[] comps = UUID.randomUUID().toString().split("-");
		ref = comps[comps.length - 1];
	}
	
	public PaymentVO(BigDecimal fareAmount) {
		this();
		this.amount = fareAmount;
	}
	
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

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getCustomerNrc() {
		return customerNrc;
	}

	public void setCustomerNrc(String customerNrc) {
		this.customerNrc = customerNrc;
	}

	public PaymentOption getPaymentOption() {
		return paymentOption;
	}

	public void setPaymentOption(PaymentOption paymentOption) {
		this.paymentOption = paymentOption;
	}	
	
}
