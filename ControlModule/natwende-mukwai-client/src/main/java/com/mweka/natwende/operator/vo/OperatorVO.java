package com.mweka.natwende.operator.vo;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.types.OperatorName;

public class OperatorVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6573381638135915914L;
	
	private OperatorName name;
	

	public OperatorVO(OperatorName name) {
		super();
		this.name = name;
	}
	
	public OperatorVO(long id) {
		super();
		setId(id);
	}

	public OperatorVO() {
		super();
	}

	public OperatorName getName() {
		return name;
	}

	public void setName(OperatorName name) {
		this.name = name;
	}
	
}
