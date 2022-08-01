package com.mweka.natwende.media.entity;

import com.mweka.natwende.base.BaseEntity;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Media")
public class Media extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private long length;

    @Lob
    @Basic(fetch = FetchType.LAZY)
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
