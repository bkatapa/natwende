package com.mweka.natwende.payment.vo;

import java.io.Serializable;

public class PaymentEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4829691977365560855L;
	private final PaymentVO payment;
	
	public PaymentEvent(PaymentVO payment) {
		this.payment = payment;
	}
	
	public PaymentVO getPayment() {
		return payment;
	}
}
