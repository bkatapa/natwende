package com.mweka.natwende.log.vo;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.types.ActivityType;

public class ActivityLogVO extends BaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3231821169361589381L;

	private String userSessionId;	
	private String username;
	private ActivityType activityType;
	private String data;	
	
	public String getUserSessionId() {
		return userSessionId;
	}
	
	public void setUserSessionId(String userSessionId) {
		this.userSessionId = userSessionId;
	}	
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
