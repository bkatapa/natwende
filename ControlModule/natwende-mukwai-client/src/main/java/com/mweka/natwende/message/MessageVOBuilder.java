package com.mweka.natwende.message;

import java.io.Serializable;

import com.mweka.natwende.types.MessageSeverity;

public class MessageVOBuilder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1877797801043468816L;

	private MessageVO message;
	
	public MessageVOBuilder() {
		message = new MessageVO();
	}
	
	public MessageVOBuilder(MessageSeverity severity) {
		this();
		message.setSeverity(severity);
	}
	
	public MessageVOBuilder severity(MessageSeverity severity) {
		message.setSeverity(severity);
		return this;
	}
	
	public MessageVOBuilder summary(String summary) {
		message.setSummary(summary);
		return this;
	}
	
	public MessageVOBuilder detail(String detail) {
		message.setDetail(detail);
		return this;
	}
	
	public MessageVO build() {
		return message;
	}
}
