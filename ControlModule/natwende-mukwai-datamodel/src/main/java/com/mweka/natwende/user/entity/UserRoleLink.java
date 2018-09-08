package com.mweka.natwende.user.entity;

import com.mweka.natwende.base.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "UserRoleLink")
@XmlRootElement
@NamedQueries({
@NamedQuery(name = "UserRoleLink.findAll", query = "SELECT u FROM UserRoleLink u"),
@NamedQuery(name = "UserRoleLink.findById", query = "SELECT u FROM UserRoleLink u WHERE u.id = :id"),
@NamedQuery(name = "UserRoleLink.findByUserId", query = "SELECT u FROM UserRoleLink u WHERE u.user.id = :userId")})
public class UserRoleLink extends BaseEntity {

	@Transient
    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "User_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User user;
    
    @JoinColumn(name = "Roles_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Role role;

    public UserRoleLink() {
    }

    public UserRoleLink(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "com.mweka.natwende.entity.UserRoleLink[ id=" + id + " ]";
    }

}
