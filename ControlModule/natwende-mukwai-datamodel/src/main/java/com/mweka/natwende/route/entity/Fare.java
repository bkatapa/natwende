package com.mweka.natwende.route.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;

@Entity
@Table(name = "fare", uniqueConstraints = {@UniqueConstraint(columnNames = {"from_id", "to_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Fare.QUERY_FIND_ALL, query=" SELECT f FROM Fare f "),
    @NamedQuery(name = Fare.QUERY_FIND_BY_ROUTE, query = " SELECT frl.fare FROM FareRouteLink frl WHERE frl.route.id = :routeId AND frl.route.status = :status ")
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
	
	/**
	 * Query parameters
	 */

	@JoinColumn(name = "from_id", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
	private Stop from;
	
	@JoinColumn(name = "to_id", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
	private Stop to;
	
	private BigDecimal amount;
	
	public Stop getFrom() {
		return from;
	}
	
	public void setFrom(Stop from) {
		this.from = from;
	}
	
	public Stop getTo() {
		return to;
	}
	
	public void setTo(Stop to) {
		this.to = to;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
