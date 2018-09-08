package com.mweka.natwende.route.entity;

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

@Entity
@Table(name = "FareRouteLink", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fare_id", "route_id"})
})
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = FareRouteLink.QUERY_FIND_ALL, query = "SELECT frl FROM FareRouteLink frl"),
	@NamedQuery(name = FareRouteLink.QUERY_FIND_ALL_BY_STATUS, query = "SELECT frl FROM FareRouteLink frl WHERE frl.status = :status"),
	@NamedQuery(name = FareRouteLink.QUERY_FIND_LIST_BY_ROUTE_ID, query = "SELECT frl FROM FareRouteLink frl WHERE frl.route.id = :routeId")
})
public class FareRouteLink extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8055030293728832260L;
	/**
	 * Named queries
	 */
	public static final String QUERY_FIND_ALL = "FareRouteLink.findAll";
	public static final String QUERY_FIND_ALL_BY_STATUS = "FareRouteLink.findAllByStatus";
	public static final String QUERY_FIND_LIST_BY_ROUTE_ID = "FareRouteLink.findListByRouteId";
	
	/**
	 * Query parameters
	 */
	public static transient final String PARAM_FARE_ROUTE_LINK_ID = "fareRouteLinkId";
	
	@JoinColumn(name = "fare_id", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Fare fare;
	 
    @JoinColumn(name = "route_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Route route;

	public Fare getFare() {
		return fare;
	}

	public void setFare(Fare fare) {
		this.fare = fare;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}
	
}
