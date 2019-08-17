package com.mweka.natwende.base.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mweka.natwende.types.Status;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author
 *
 */
@XmlRootElement
@JsonIgnoreProperties
public abstract class BaseVO implements Serializable {

    /**
	 * 
	 */
	private static final transient long serialVersionUID = -424530304670915375L;
	/**
     * Length of the uniqueId field.
     */
    static final transient int UID_LENGTH = 36;
    /**
     * Starting value used in calculating the object hash.
     */
    static final transient int HASH_SEED = 5;
    /**
     * Multiplier used in calculating the object hash.
     */
    static final transient int HASH_MULIPLIER = 97;
    /**
     * Right shift value used in calculating the object hash.
     */
    static final transient int HASH_RIGHTSHIFT = 32;

    /**
     * Synthetic object Id for persistence.
     */
    @XmlTransient
    private long id = -1L;

    /**
     * Date at which object was inserted to persistence layer.
     */
    @XmlTransient
    private Date insertDate;

    /**
     * Date at which object was updated to persistence layer.
     */
    @XmlTransient
    private Date updateDate;

    /**
     * Object UniqueId.
     */
    @XmlTransient
    @Size(min = UID_LENGTH, max = UID_LENGTH, message = "GUID Required")
    private String uniqueId;

    @XmlTransient
    private Status status = Status.ACTIVE;

    /**
     * Version field for object.
     */
    @XmlTransient
    private long version;

    @XmlTransient
    private Boolean selected;

    /**
     * Explicit no parameter constructor.
     */
    public BaseVO() {
    }

    /**
     * Explicit all field constructor.
     *
     * @param id long to set as id
     * @param insertDate Date to set as insertDate
     * @param updateDate Date to set as updateDate
     * @param paramUniqueId String to set as uniqueId
     * @param version long to set as version
     */
    public BaseVO(final long id, final Date insertDate, final Date updateDate, final String paramUniqueId, final long version) {
        if (insertDate != null) {
            this.insertDate = new Date(insertDate.getTime());
        }
        if (updateDate != null) {
            this.updateDate = new Date(updateDate.getTime());
        }
        this.uniqueId = paramUniqueId;
        this.version = version;
    }

    /**
     * Returns the object id.
     *
     * @return long id
     */
    public final long getId() {
        return id;
    }

    /**
     * Sets the object id.
     *
     * @param Id long to set as id
     */
    public final void setId(final long Id) {
        this.id = Id;
    }

    /**
     * Returns the object insertDate.
     *
     * @return Date insertDate
     */
    public final Date getInsertDate() {
        if (this.insertDate == null) {
            return null;
        } else {
            return new Date(this.insertDate.getTime());
        }
    }

    /**
     * Sets the object insertDate.
     *
     * @param insertDate Date to set as insertDate
     */
    public final void setInsertDate(final Date insertDate) {
        if (insertDate == null) {
            this.insertDate = null;
        } else {
            this.insertDate = new Date(insertDate.getTime());
        }
    }

    /**
     * Returns the object updateDate.
     *
     * @return Date updateDate
     */
    public final Date getUpdateDate() {
        if (this.updateDate == null) {
            return null;
        } else {
            return new Date(this.updateDate.getTime());
        }
    }

    /**
     * Sets the object updateDate.
     *
     * @param updateDate Date to set as updateDate
     */
    public final void setUpdateDate(final Date updateDate) {
        if (updateDate == null) {
            this.updateDate = null;
        } else {
            this.updateDate = new Date(updateDate.getTime());
        }
    }

    /**
     * Returns the object uniqueId.
     *
     * @return String uniqueId
     */
    public final String getUniqueId() {
        return uniqueId;
    }

    /**
     * Sets th object uniqueId.
     *
     * @param uniqueId String to set as uniqueId
     */
    public final void setUniqueId(final String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * Returns the object version.
     *
     * @return long version
     */
    public final long getVersion() {
        return version;
    }

    /**
     * Sets the object version.
     *
     * @param version long to set as version
     */
    public final void setVersion(final long version) {
        this.version = version;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "BaseVO{" + "id=" + id + ", insertDate=" + insertDate
                + ", updateDate=" + updateDate + ", uniqueId=" + uniqueId
                + ", version=" + version + '}';
    }

    @Override
    public int hashCode() {
        int hash = HASH_SEED;
        hash = HASH_MULIPLIER * hash + (int) (this.id ^ (this.id >>> HASH_RIGHTSHIFT));
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || !(obj instanceof BaseVO)) {
            return false;
        } else {
            final BaseVO other = (BaseVO) obj;
            return this.id == other.id;
        }
    }
    
}
