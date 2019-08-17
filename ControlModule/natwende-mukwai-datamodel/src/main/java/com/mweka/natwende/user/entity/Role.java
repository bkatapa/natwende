/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.user.entity;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.types.RoleType;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Roles", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"roleType"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Role.QUERY_FIND_ALL, query = "SELECT r FROM Role r"),
    @NamedQuery(name = Role.QUERY_FIND_BY_ID, query = "SELECT r FROM Role r WHERE r.id = :_id"),
    @NamedQuery(name = Role.QUERY_FIND_BY_ROLETYPE, query = "SELECT r FROM Role r WHERE r.roleType = :_roleType"),
    @NamedQuery(name = Role.QUERY_FIND_BY_USER_ID_AND_STATUS, query = "SELECT r FROM Role r JOIN r.userRoleLinkList url WHERE url.status = :status and url.user.id = :_userId"),
    @NamedQuery(name = Role.QUERY_FIND_FROM_USER_ROLE_LINK_BY_USER_ID_AND_STATUS, query = "SELECT url.role FROM UserRoleLink url WHERE url.role.status = :_status and url.user.id = :_userId")
})
public class Role extends BaseEntity {

    @Transient
	private static final long serialVersionUID = 1L;
    
    // Named Query Constants
    public static final transient String QUERY_FIND_ALL = "Role.findAll";
    public static final transient String QUERY_FIND_BY_ID = "Role.findById";
    public static final transient String QUERY_FIND_BY_ROLETYPE = "Role.findByRoleType";
    public static final transient String QUERY_FIND_BY_USER_ID_AND_STATUS = "Role.findByUserIdAndStatus";
    public static final transient String QUERY_FIND_FROM_USER_ROLE_LINK_BY_USER_ID_AND_STATUS = "Role.findFromUserRoleLinkByUserIdAndStatus";
    
    // Query Parameters
    public static final transient String PARAM_ROLE_ID = "roleId";
    public static final transient String PARAM_USER_ID = "_userId";
    public static final transient String PARAM_ROLE_TYPE = "_roleType";
    public static final transient String PARAM_STATUS = "_status";

    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.LAZY)
    private List<UserRoleLink> userRoleLinkList;

    public Role() {
    }

    public Role(Integer id) {
        this.id = id;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    @XmlTransient
    public List<UserRoleLink> getUserRoleLinkList() {
        return userRoleLinkList;
    }

    public void setUserRoleLinkList(List<UserRoleLink> userRoleLinkList) {
        this.userRoleLinkList = userRoleLinkList;
    }

    @Override
    public String toString() {
        return "com.mweka.natwende.entity.Roles[ id=" + id + " ]";
    }

}
