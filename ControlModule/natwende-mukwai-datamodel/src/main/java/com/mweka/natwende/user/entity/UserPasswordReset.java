package com.mweka.natwende.user.entity;

import com.mweka.natwende.base.BaseEntity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "UserPasswordReset",uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserPasswordReset.findByUsername", query = "SELECT upr FROM UserPasswordReset upr JOIN upr.user u WHERE u.username = :username ")
})
public class UserPasswordReset extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    // Named Queries
    public static final String FIND_BY_USERNAME = "UserPasswordReset.findByUsername";
    
    // Query Parameters
    public static final String PARAM_USERNAME = "username";

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiry_date")
    private Date expiryDate;

    private String email;
    
    @Column(name = "reset_pin")
    private String resetPin;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getResetPin() {
	return resetPin;
    }

    public void setResetPin(String resetPin) {
	this.resetPin = resetPin;
    }
}
