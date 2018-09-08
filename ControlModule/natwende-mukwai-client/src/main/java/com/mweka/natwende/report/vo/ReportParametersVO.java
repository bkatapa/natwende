package com.mweka.natwende.report.vo;

import com.mweka.natwende.base.vo.BaseVO;

public class ReportParametersVO extends BaseVO {
	private static final long serialVersionUID = 1L;

	private String paramKey;
	private Object paramValue;

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public Object getParamValue() {
		return paramValue;
	}

	public void setParamValue(Object paramValue) {
		this.paramValue = paramValue;
	}		
	
}
