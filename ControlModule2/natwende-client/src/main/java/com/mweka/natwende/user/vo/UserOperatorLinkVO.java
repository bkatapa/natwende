package com.mweka.natwende.user.vo;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.operator.vo.OperatorVO;

public class UserOperatorLinkVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2048450336268212332L;
	
	private UserVO user;
	private OperatorVO operator;
	
	public UserOperatorLinkVO(UserVO user, OperatorVO operator) {
		super();
		this.user = user;
		this.operator = operator;
	}
	
	public UserOperatorLinkVO() {
		super();
	}

	public UserVO getUser() {
		return user;
	}
	
	public void setUser(UserVO user) {
		this.user = user;
	}
	
	public OperatorVO getOperator() {
		return operator;
	}
	
	public void setOperator(OperatorVO operator) {
		this.operator = operator;
	}

}
