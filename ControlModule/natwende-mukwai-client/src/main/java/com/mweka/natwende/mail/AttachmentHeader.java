package com.mweka.natwende.mail;

import java.io.Serializable;

public class AttachmentHeader implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2229089003156024081L;
	String name;
    String mimeType;

    MailAttachment mailAttachment;

    MailMessage mailMessage;
	private String contentId;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getMimeType() {
	return mimeType;
    }

    public void setMimeType(String mimeType) {
	this.mimeType = mimeType;
    }

    public MailAttachment getMailAttachment() {
	return mailAttachment;
    }

    public void setMailAttachment(MailAttachment mailAttachment) {
	this.mailAttachment = mailAttachment;
    }

    public MailMessage getMailMessage() {
	return mailMessage;
    }

    public void setMailMessage(MailMessage mailMessage) {
	this.mailMessage = mailMessage;
    }

    @Override
    public String toString() {
	return "AttachmentHeader{" + "name=" + name + ", mimeType=" + mimeType + ", mailMessage=" + mailMessage + '}';
    }
    
    public void setContentId(String value) {
    	contentId = value;
    }

	public String getContentId() {
		return contentId;
	}

}
