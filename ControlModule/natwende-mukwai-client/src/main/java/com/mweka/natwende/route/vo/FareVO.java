package com.mweka.natwende.route.vo;

import java.math.BigDecimal;

import com.mweka.natwende.base.vo.BaseVO;

public class FareVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8155086701371898334L;

	private StopVO from;
	private StopVO to;
	private BigDecimal amount;
	
	public StopVO getFrom() {
		return from;
	}
	
	public void setFrom(StopVO from) {
		this.from = from;
	}
	
	public StopVO getTo() {
		return to;
	}
	
	public void setTo(StopVO to) {
		this.to = to;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
