package com.mweka.natwende.notification.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.types.MessageSeverity;

@Entity
@Table(name="Message")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id")
})
public class Message extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4317020259942186829L;
	
	@Enumerated(EnumType.STRING)
	private MessageSeverity severity;
	
	private String summary;
	private String detail;
	
	public MessageSeverity getSeverity() {
		return severity;
	}
	
	public void setSeverity(MessageSeverity severity) {
		this.severity = severity;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getDetail() {
		return detail;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}	
	
}
