package com.mweka.natwende.log.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.types.ActivityType;

@Entity
@Table(name = "ActivityLog")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = ActivityLog.QUERY_FIND_ALL, query=" SELECT al FROM ActivityLog al "),
    @NamedQuery(name = ActivityLog.QUERY_FIND_BY_USERNAME, query=" SELECT al FROM ActivityLog al WHERE al.username = :username "),
    @NamedQuery(name = ActivityLog.QUERY_FIND_BY_STATUS, query=" SELECT al FROM ActivityLog al WHERE al.status = :status ")
})
public class ActivityLog extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2060608792741982248L;
	
	// Named Queries
	public static transient final String QUERY_FIND_ALL = "ActivityLog.findAll";
	public static transient final String QUERY_FIND_BY_USERNAME = "ActivityLog.findByUsername";
	public static transient final String QUERY_FIND_BY_STATUS = "ActivityLog.findByStatus";
		
	// Query Parameters
	public static transient final String PARAM_CARD_ID = "cardId";

	@Column(name = "user_session_id")
	private String userSessionId;	
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "data")
	private String data;
	
	@Column(name = "activity_type")
	@Enumerated(EnumType.STRING)
	private ActivityType activityType;
	
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}
	
}
