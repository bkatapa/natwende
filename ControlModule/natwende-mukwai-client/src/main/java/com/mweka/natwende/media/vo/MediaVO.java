package com.mweka.natwende.media.vo;

import javax.validation.constraints.NotNull;

import com.mweka.natwende.base.vo.BaseVO;

public class MediaVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private long length;

    @NotNull
    private byte[] data;

    private String fileName;

    private String mime;

    public long getLength() {
	return length;
    }

    public void setLength(long length) {
	this.length = length;
    }

    public byte[] getData() {
	return data;
    }

    public void setData(byte[] data) {
	this.data = data;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public String getMime() {
	return mime;
    }

    public void setMime(String mime) {
	this.mime = mime;
    }

}
