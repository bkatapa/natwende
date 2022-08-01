package com.mweka.natwende.notification.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.types.NotificationStatus;
import com.mweka.natwende.types.NotificationType;

@Entity
@Table(name="Notification")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Notification.findById", query = " SELECT n FROM Notification n WHERE n.id = :id ")
})
public class Notification extends BaseEntity {	
	private static final long serialVersionUID = 1L;	
	
	@Temporal(TemporalType.TIMESTAMP)	
	private Date submitDate;
	
	@Enumerated(EnumType.STRING) @Column(length=32, nullable=false)
	@NotNull
	private NotificationStatus notitifcationStatus;
	
	@Enumerated(EnumType.STRING) @Column(length=32, nullable=false)
	@NotNull
	private NotificationType notitifcationType;
	
	@Column(length=255)
	@NotNull @Size(max=255)
	private String subject;
	
	@OneToOne
	@JoinColumn(name = "message_id")
	private Message message;

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

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public NotificationType getNotitifcationType() {
		return notitifcationType;
	}

	public void setNotitifcationType(NotificationType notitifcationType) {
		this.notitifcationType = notitifcationType;
	}	
	
}
