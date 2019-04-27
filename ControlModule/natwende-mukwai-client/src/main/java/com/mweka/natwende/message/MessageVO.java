package com.mweka.natwende.message;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.types.MessageSeverity;

public class MessageVO extends BaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1380986901935098827L;

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
