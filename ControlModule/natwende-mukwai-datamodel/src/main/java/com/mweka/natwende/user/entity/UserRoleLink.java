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
@NamedQuery(name = UserRoleLink.QUERY_FIND_ALL, query = "SELECT u FROM UserRoleLink u"),
@NamedQuery(name = UserRoleLink.QUERY_FIND_BY_ID, query = "SELECT u FROM UserRoleLink u WHERE u.id = :id"),
@NamedQuery(name = UserRoleLink.QUERY_FIND_BY_USER_ID, query = "SELECT u FROM UserRoleLink u WHERE u.user.id = :userId"),
@NamedQuery(name = UserRoleLink.QUERY_FIND_BY_USER_ID_AND_ROLE_ID, query = " SELECT url FROM UserRoleLink url WHERE url.user.id = :userId AND url.role.id = :roleId ")})
public class UserRoleLink extends BaseEntity {

	@Transient
    private static final long serialVersionUID = 1L;
	
	// Named Queries
	public static transient final String QUERY_FIND_ALL = "UserRoleLink.findAll";
	public static transient final String QUERY_FIND_BY_ID = "UserRoleLink.findById";
	public static transient final String QUERY_FIND_BY_USER_ID = "UserRoleLink.findByUserId";
	public static transient final String QUERY_FIND_BY_USER_ID_AND_ROLE_ID = "UserRoleLink.findByUserIdAndRoleId";

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
