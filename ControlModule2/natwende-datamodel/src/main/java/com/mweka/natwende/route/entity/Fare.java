package com.mweka.natwende.route.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.operator.entity.Operator;

@Entity
@Table(name = "Fare", uniqueConstraints = {@UniqueConstraint(columnNames = {"operator_id", "stretch_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Fare.QUERY_FIND_ALL, query=" SELECT f FROM Fare f "),
    @NamedQuery(name = Fare.QUERY_FIND_BY_STRETCH_ID, query = " SELECT f FROM Fare f WHERE f.stretch.id = :stretchId "),
    @NamedQuery(name = Fare.QUERY_FIND_BY_ROUTE_ID_AND_OPERATOR_ID, query = " SELECT f FROM Fare f, RouteStretchLink rsl WHERE f.stretch.id = rsl.stretch.id AND f.operator.id = :operatorId AND rsl.route.id = :routeId "),
    @NamedQuery(name = Fare.QUERY_FIND_BY_OPERATOR_ID_AND_STRETCH_ID, query = " SELECT f FROM Fare f WHERE f.stretch.id = :stretchId AND f.operator.id = :operatorId ")
})
public class Fare extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3346406972469712735L;
	
	/**
	 * Named queries
	 */
	public static final String QUERY_FIND_ALL = "Fare.findAll";
	public static final String QUERY_FIND_BY_ROUTE = "Fare.findByRoute";
	public static transient final String QUERY_FIND_BY_ROUTE_ID_AND_OPERATOR_ID = "Fare.findByRouteIdAndOperatorId";
	public static transient final String QUERY_FIND_BY_OPERATOR_ID_AND_STRETCH_ID = "Fare.findByOperatorIdAndStretchId";
	public static transient final String QUERY_FIND_BY_STRETCH_ID = "Fare.findByStretchId";
	
	/**
	 * Query parameters
	 */	
	public static transient final String PARAM_FARE_ID = "fareId";
	
	@Transient
	private boolean global;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "operator_id", referencedColumnName = "id", nullable = false)
	private Operator operator;

	@JoinColumn(name = "stretch_id", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
	private Stretch stretch;
	
	@NotNull
	@Column(nullable = false, scale = 12)
	private BigDecimal amount = BigDecimal.ZERO;	
	
	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Stretch getStretch() {
		return stretch;
	}

	public void setStretch(Stretch stretch) {
		this.stretch = stretch;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
