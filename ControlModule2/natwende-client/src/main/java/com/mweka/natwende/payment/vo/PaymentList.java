package com.mweka.natwende.payment.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "paymentList")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -313313401410976938L;
	
	@XmlElement(name = "payment")
	private List<PaymentVO> paymentList = null;
	
	public PaymentList() {}
	
	public PaymentList(List<PaymentVO> paymentList) {
		this.paymentList = paymentList;
	}

	public List<PaymentVO> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<PaymentVO> paymentList) {
		this.paymentList = paymentList;
	}	
	
}
