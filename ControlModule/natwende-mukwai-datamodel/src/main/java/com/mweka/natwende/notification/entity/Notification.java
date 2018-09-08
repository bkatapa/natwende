package com.mweka.natwende.notification.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.types.NotificationStatus;

@Entity
@Table(name="Notification")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Notification.findById", query = "SELECT n from Notification n where n.id = :id")
//	,
//	@NamedQuery(name = "Notification.findBySenderId", query = "SELECT n from Notification n where n.sender.id = :senderId"),
	//@NamedQuery(name = "Notification.findByTradingRelationship", query="SELECT n from Notification n ????")
})
public class Notification extends BaseEntity {	
	private static final long serialVersionUID = 1L;
		
//	@ManyToOne @JoinColumn(name="sending_company_id")	
//	private ParkingSite sender;	
	
	@Temporal(TemporalType.TIMESTAMP)	
	private Date submitDate;
	
	@Enumerated(EnumType.STRING) @Column(length=32, nullable=false)
	@NotNull
	private NotificationStatus notitifcationStatus;
	
	@Column(length=255)
	@NotNull @Size(max=255)
	private String subject;
	
	@Lob
	private String message;

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
