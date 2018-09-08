package com.mweka.natwende.user.entity;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.operator.entity.Operator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "UserOperatorLink")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "UserOperatorLink.findAll", query = "SELECT u FROM UserOperatorLink u"),
		@NamedQuery(name = "UserOperatorLink.findById", query = "SELECT u FROM UserOperatorLink u WHERE u.id = :id"),
		@NamedQuery(name = "UserOperatorLink.findByUserIdandTenantId", query = "SELECT u FROM UserOperatorLink u WHERE u.user.id = :userId and u.operator.id = :operatorId"),
		@NamedQuery(name = "UserOperatorLink.findByTenantId", query = "SELECT u FROM UserOperatorLink u WHERE u.operator.id = :operatorId"),
		@NamedQuery(name = "UserOperatorLink.findByUserId", query = "SELECT u FROM UserOperatorLink u WHERE u.user.id = :userId")// ,
		// @NamedQuery(name = "UserTenantLink.findActiveByTenantId", query =
		// "SELECT u FROM UserTenantLink u WHERE u.status = 'ACTIVE' and
		// u.tenant.id = :tenantId"),
		// @NamedQuery(name = "UserTenantLink.findActiveByUserId", query =
		// "SELECT u FROM UserTenantLink u WHERE u.status = 'ACTIVE' and
		// u.user.id = :userId")
})
public class UserOperatorLink extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private User user;
	
	@JoinColumn(name = "operator_id", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Operator operator;

	public UserOperatorLink() {
	}

	public UserOperatorLink(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "com.mweka.natwende.parking.entity.UserOperatorLink[ id=" + id + " ]";
	}

}
