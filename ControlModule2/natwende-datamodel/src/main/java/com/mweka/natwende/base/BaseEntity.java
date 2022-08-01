package com.mweka.natwende.base;

import com.mweka.natwende.types.Status;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;


@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    /**
	 * 
	 */
	private static final transient long serialVersionUID = 8755842440377156227L;
	
	@Transient
	public static final String PARAM_STATUS = "status";

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id = -1L;
    
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Insert Date Required")
    protected Date insertDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Update Date Required")
    protected Date updateDate;
    
    @NotNull(message = "UniqueID Required")
    @Column(unique=true)
    protected String uniqueId;
    
    @Version
    protected long version;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;

    @PrePersist
    public void initData() {
        insertDate = new Date();
        updateDate = new Date();
        if (uniqueId == null) {
        	uniqueId = UUID.randomUUID().toString();
        }
    }

    @PreUpdate
    public void updateData() {
        updateDate = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BaseEntity{" + "id=" + id + ", insertDate=" + insertDate + ", updateDate=" + updateDate + ", uniqueId=" + uniqueId + ", version=" + version + ", status=" + status + '}';
	}
	
	
}