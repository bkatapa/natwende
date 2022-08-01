package com.mweka.natwende.notification.vo;

import java.util.Date;

import com.mweka.natwende.types.NotificationStatus;
import com.mweka.natwende.util.BaseSearchVO;

public class NotificationSearchVO extends BaseSearchVO {	
	private static final long serialVersionUID = 1L;
	
	private Long senderCompanyId;
	private Long receivingCompanyId;
	
	private String subject;
	
	private NotificationStatus notificationStatus;
	
	private Date fromDate;
	
	private Date toDate;

	public Long getSenderCompanyId() {
		return senderCompanyId;
	}

	public void setSenderCompanyId(Long senderCompanyId) {
		this.senderCompanyId = senderCompanyId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public NotificationStatus getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(NotificationStatus notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getReceivingCompanyId() {
		return receivingCompanyId;
	}

	public void setReceivingCompanyId(Long receivingCompanyId) {
		this.receivingCompanyId = receivingCompanyId;
	}

}
