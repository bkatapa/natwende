package com.mweka.natwende.operator.vo;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.route.vo.RouteVO;

public class OperatorRouteLinkVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OperatorVO operator;
	private RouteVO route;
	
	public OperatorRouteLinkVO() {		
	}
	
	public OperatorRouteLinkVO(OperatorVO operator, RouteVO route) {
		this.operator = operator;
		this.route = route;
	}
	
	public OperatorVO getOperator() {
		return operator;
	}
	
	public void setOperator(OperatorVO operator) {
		this.operator = operator;
	}
	
	public RouteVO getRoute() {
		return route;
	}
	
	public void setRoute(RouteVO route) {
		this.route = route;
	}	

}
