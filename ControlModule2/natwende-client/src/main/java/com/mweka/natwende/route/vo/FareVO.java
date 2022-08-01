package com.mweka.natwende.route.vo;

import java.math.BigDecimal;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.operator.vo.OperatorVO;

public class FareVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8155086701371898334L;

	private boolean global;
	private OperatorVO operator;
	private StretchVO stretch;
	private BigDecimal amount;
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public OperatorVO getOperator() {
		return operator;
	}

	public void setOperator(OperatorVO operator) {
		this.operator = operator;
	}

	public StretchVO getStretch() {
		return stretch;
	}

	public void setStretch(StretchVO stretch) {
		this.stretch = stretch;
	}
	
}
