package com.mweka.natwende.operator.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.route.entity.Route;

@Entity
@Table(name = "OperatorRouteLink", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"operator_id", "route_id"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = OperatorRouteLink.QUERY_FIND_ALL, query = "SELECT orl FROM OperatorRouteLink orl"),
    @NamedQuery(name = OperatorRouteLink.QUERY_FIND_LIST_BY_OPERATOR_ID, query = "SELECT orl FROM OperatorRouteLink orl WHERE orl.operator.id = :operatorId"),
})
public class OperatorRouteLink extends BaseEntity {

	/**
	 * 
	 */
	private static final transient long serialVersionUID = 352311306212903390L;
	
	/**
	 * Named queries
	 */
	public static final transient String QUERY_FIND_ALL = "OperatorRouteLink.findAll";
	public static final transient String QUERY_FIND_LIST_BY_OPERATOR_ID = "OperatorRouteLink.findListByOperatorId";
	
	/**
	 * Query parameters
	 */
	public static final transient String PARAM_OPERATOR_ID = "operatorId";
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "operator_id")
	private Operator operator;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "route_id")
	private Route route;

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}	
	
}
