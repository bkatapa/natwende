package com.mweka.natwende.mail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class MailAttachment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1256186510220226270L;
	byte[] data;

	public byte[] getData() throws IOException {
		if (this.data == null) {
			return null;
		} else {
			ByteArrayOutputStream getArray = new ByteArrayOutputStream();
			getArray.write(data);
			return getArray.toByteArray();
		}
	}

	public void setData(final byte[] data) throws IOException {
		if (data == null) {
			this.data = null;
		} else {
			ByteArrayOutputStream getArray = new ByteArrayOutputStream();
			getArray.write(data);
			this.data = getArray.toByteArray();
		}
	}

	@Override
	public String toString() {
		return "MailAttachment{" + "data=" + data.toString() + '}';
	}

}
