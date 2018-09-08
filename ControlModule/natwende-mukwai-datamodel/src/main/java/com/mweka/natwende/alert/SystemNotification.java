package com.mweka.natwende.alert;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.media.entity.Media;
import com.mweka.natwende.operator.entity.Operator;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "SystemNotification")
@NamedQueries({
		@NamedQuery(name = SystemNotification.NAMED_QUERY_FIND_BY_PARKINGSITE_ID, query = "select s from SystemNotification s WHERE s.operator.id = :_operatorId")})
public class SystemNotification extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Transient
	public static final String NAMED_QUERY_FIND_BY_PARKINGSITE_ID = "SystemNotification.findByParkingSiteId";
	@Transient
	public static final String NAMED_PARAMETER_PARKINGSITE_ID = "_parkingSiteId";
	
	
	@Temporal(TemporalType.DATE)
	private Date notificationDate;
	
	@Size(max = 255)
	private String subject;
	
	@Lob
	private String message;
	
	@OneToOne
	@JoinColumn(name = "Attachement_id")
	private Media attachment;
	
	@ManyToOne
	@JoinColumn(name = "Operator_id")
	private Operator operator;

	public Date getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(Date notificationDate) {
		this.notificationDate = notificationDate;
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

	public Media getAttachment() {
		return attachment;
	}

	public void setAttachment(Media attachment) {
		this.attachment = attachment;
	}	

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "SystemNotification{" + "notificationDate=" + notificationDate + ", subject=" + subject + ", message=" + message + ", attachment=" + attachment + ", operator=" + operator + '}';
	}
		
}
