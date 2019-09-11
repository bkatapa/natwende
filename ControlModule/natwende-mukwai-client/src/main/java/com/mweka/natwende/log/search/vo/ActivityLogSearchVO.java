package com.mweka.natwende.log.search.vo;

import com.mweka.natwende.types.ActivityType;
import com.mweka.natwende.util.BaseSearchVO;

public class ActivityLogSearchVO extends BaseSearchVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8881423851501836708L;
	
	private String userSessionId;	
	private String username;
	private ActivityType activityType;
	
	public ActivityLogSearchVO() {		
	}
	
	public ActivityLogSearchVO(String userSessionId) {
		this.userSessionId = userSessionId;
	}
	
	public boolean hasFilters() {	
		if (userSessionId != null && !"".equals(userSessionId.trim())) {
			return true;
		}
		if (username != null && !"".equals(username.trim())) {
			return true;
		}
		if (activityType != null) {
			return true;
		}
		return false;
	}

	public void clearSearch() {		
		this.userSessionId = null;
		this.username = null;
		this.activityType = null;
	}

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
	
}
