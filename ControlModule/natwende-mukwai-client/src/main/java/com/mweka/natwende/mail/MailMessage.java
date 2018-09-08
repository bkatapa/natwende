package com.mweka.natwende.mail;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MailMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	List<String> recipients;
	List<String> cc;
	List<String> bcc;

	String fromAddress;
	String body = "";
	String htmlBody = "";
	String subject;
	String companyCode;

	Set<AttachmentHeader> mediaHeader;

	public List<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(final List<String> recipients) {
		this.recipients = recipients;
	}
	
	public List<String> getBcc() {
		return bcc;
	}
	
	public void setBcc(final List<String> value) {
		this.bcc = value;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(final String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getBody() {
		return body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public Set<AttachmentHeader> getMediaHeader() {
		if (mediaHeader == null) {
			mediaHeader = new HashSet<AttachmentHeader>();
		}
		return mediaHeader;
	}

	public void setMediaHeader(final Set<AttachmentHeader> mediaHeader) {
		this.mediaHeader = mediaHeader;
	}

	public String getHtmlBody() {
		return htmlBody;
	}

	public void setHtmlBody(final String htmlBody) {
		this.htmlBody = htmlBody;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(final String companyCode) {
		this.companyCode = companyCode;
	}

	public List<String> getCc() {
	    return cc;
	}

	public void setCc(List<String> cc) {
	    this.cc = cc;
	}

	@Override
	public String toString() {
		return super.toString() + " MailMessage{" + "recipients=" + this.recipients.toArray().toString() + ", fromAddress=" + fromAddress + ", body=" + body
				+ ", subject=" + subject + ", mediaHeader=" + mediaHeader + '}';
	}

	public void addMediaHeader(String bookingVoucherPdf, byte[] byteArray) throws IOException {
		AttachmentHeader attachmentHeader = new AttachmentHeader();
		MailAttachment mailAttachment = new MailAttachment();
		mailAttachment.setData(byteArray);
		attachmentHeader.setContentId(bookingVoucherPdf);
		attachmentHeader.setMimeType("application/octect-stream");
		attachmentHeader.setName(bookingVoucherPdf);
		attachmentHeader.setMailAttachment(mailAttachment);
		getMediaHeader().add(attachmentHeader);
	}

}
