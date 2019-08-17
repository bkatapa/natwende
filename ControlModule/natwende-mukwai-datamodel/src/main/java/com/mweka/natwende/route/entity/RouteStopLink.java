package com.mweka.natwende.route.entity;

import javax.persistence.Column;
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
@Table(name = "RouteStopLink", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"route_id", "stop_id"})
})
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = RouteStopLink.QUERY_FIND_ALL, query = " SELECT rsl FROM RouteStopLink rsl "),
	@NamedQuery(name = RouteStopLink.QUERY_FIND_ALL_BY_STATUS, query = " SELECT rsl FROM RouteStopLink rsl WHERE rsl.status = :status "),
	@NamedQuery(name = RouteStopLink.QUERY_FIND_ALL_BY_ROUTE_ID, query = " SELECT rsl FROM RouteStopLink rsl WHERE rsl.route.id = :routeId "),
	@NamedQuery(name = RouteStopLink.QUERY_COUNT_BY_ROUTE_ID, query = " SELECT COUNT(rsl) FROM RouteStopLink rsl WHERE rsl.route.id = :routeId "),
	@NamedQuery(name = RouteStopLink.QUERY_FIND_BY_ROUTE_ID_AND_STOP_ID, query = " SELECT rsl FROM RouteStopLink rsl WHERE rsl.route.id = :routeId AND rsl.stop.id = :stopId ")
})
public class RouteStopLink extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6674045925578462506L;
	
	/**
	 * Named queries
	 */
	public static final String QUERY_FIND_ALL = "RouteStopLink.findAll";
	public static final String QUERY_FIND_ALL_BY_STATUS = "RouteStopLink.findAllByStatus";
	public static final String QUERY_FIND_ALL_BY_ROUTE_ID = "RouteStopLink.findAllByRouteId";
	public static transient final String QUERY_FIND_BY_ROUTE_ID_AND_STOP_ID = "RouteStopLink.findByRouteIdAndStopId";
	public static transient final String QUERY_COUNT_BY_ROUTE_ID = "RouteStopLink.countByRouteId";
	
	/**
	 * Query parameters
	 */
	public static transient final String PARAM_ROUTE_STOP_LINK_ID = "routeStopLinkId";
	
	@Column(nullable = false)
	private int stationIndex = 0;
	
	@JoinColumn(name = "route_id", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Route route;
	 
    @JoinColumn(name = "stop_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Stop stop;

    public int getStationIndex() {
		return stationIndex;
	}

	public void setStationIndex(int stationIndex) {
		this.stationIndex = stationIndex;
	}
    
	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Stop getStop() {
		return stop;
	}

	public void setStop(Stop stop) {
		this.stop = stop;
	}
    
}
