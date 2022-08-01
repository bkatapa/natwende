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
@NamedQueries({
	@NamedQuery(name = UserOperatorLink.FIND_ALL, query = " SELECT uol FROM UserOperatorLink uol "),
	@NamedQuery(name = UserOperatorLink.FIND_BY_USER_ID_AND_OPERATOR_ID, query = " SELECT uol FROM UserOperatorLink uol WHERE uol.user.id = :userId AND uol.operator.id = :operatorId ")
})
public class UserOperatorLink extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	// Named queries
	public transient static final String FIND_ALL = "UserOperatorLink.findAll";
	public transient static final String FIND_BY_USER_ID_AND_OPERATOR_ID = "UserOperatorLink.findByUserIdAndOperatorId";

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
