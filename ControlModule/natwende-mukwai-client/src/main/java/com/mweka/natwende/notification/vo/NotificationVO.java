package com.mweka.natwende.notification.vo;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.operator.vo.OperatorVO;
import com.mweka.natwende.types.NotificationStatus;

public class NotificationVO extends BaseVO {	
	private static final long serialVersionUID = 1L;
			
	private OperatorVO operator;	
		
	@NotNull
	private Date submitDate;
		
	@NotNull
	private NotificationStatus notitifcationStatus = NotificationStatus.SAVED;
		
	@NotNull @Size(min=1,max=255, message="Notification subject is required") 
	private String subject;
	
	private String message;

	public OperatorVO getOperator() {
		return operator;
	}

	public void setOperator(OperatorVO operator) {
		this.operator = operator;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public NotificationStatus getNotitifcationStatus() {
		return notitifcationStatus;
	}

	public void setNotitifcationStatus(NotificationStatus notitifcationStatus) {
		this.notitifcationStatus = notitifcationStatus;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
