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
@Table(name = "RouteStretchLink", uniqueConstraints = {@UniqueConstraint(columnNames = {"route_id", "stretch_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = RouteStretchLink.QUERY_FIND_ALL, query=" SELECT rsl FROM RouteStretchLink rsl "),
    @NamedQuery(name = RouteStretchLink.QUERY_FIND_ALL_BY_STATUS, query = " SELECT rsl FROM RouteStretchLink rsl WHERE rsl.status = :status "),
    @NamedQuery(name = RouteStretchLink.QUERY_FIND_BY_ROUTE_ID, query = " SELECT rsl FROM RouteStretchLink rsl WHERE rsl.route.id = :routeId "),
    @NamedQuery(name = RouteStretchLink.QUERY_FIND_BY_STRETCH_ID, query = " SELECT rsl FROM RouteStretchLink rsl WHERE rsl.stretch.id = :stretchId "),
    @NamedQuery(name = RouteStretchLink.QUERY_FIND_BY_ROUTE_ID_AND_STRETCH_ID, query = " SELECT rsl FROM RouteStretchLink rsl WHERE rsl.route.id = :routeId AND rsl.stretch.id = :stretchId "),
    @NamedQuery(name = RouteStretchLink.QUERY_FIND_STRETCHLIST_BY_ROUTE_ID, query = " SELECT rsl.stretch FROM RouteStretchLink rsl WHERE rsl.route.id = :routeId "),
    @NamedQuery(name = RouteStretchLink.QUERY_FIND_ROUTELIST_BY_STRETCH_ID, query = " SELECT rsl.route FROM RouteStretchLink rsl WHERE rsl.stretch.id = :stretchId "),    
    @NamedQuery(name = RouteStretchLink.QUERY_COUNT_BY_ROUTE_ID, query = " SELECT COUNT(rsl) FROM RouteStretchLink rsl WHERE rsl.route.id = :routeId "),
    @NamedQuery(name = RouteStretchLink.QUERY_DELETE_BY_ROUTE_ID, query = " DELETE FROM RouteStretchLink rsl WHERE rsl.route.id = :routeId "),
    @NamedQuery(name = RouteStretchLink.QUERY_DELETE_BY_STRETCH_ID, query = " SELECT rsl FROM RouteStretchLink rsl WHERE rsl.stretch.id = :stretchId "),
    @NamedQuery(name = RouteStretchLink.QUERY_DELETE_BY_ROUTE_ID_AND_STRETCH_ID, query = " DELETE FROM RouteStretchLink rsl WHERE rsl.route.id = :routeId AND rsl.stretch.id = :stretchId "),
})
public class RouteStretchLink extends BaseEntity {

	/**
	 * 
	 */
	private static transient final long serialVersionUID = 1423033513948398006L;
	
	/**
	 * Named queries
	 */
	public static transient final String QUERY_FIND_ALL = "RouteStretchLink.findAll";
	public static transient final String QUERY_FIND_ALL_BY_STATUS = "RouteStretchLink.findAllByStatus";
	public static transient final String QUERY_FIND_BY_ROUTE_ID = "RouteStretchLink.findByRouteId";
	public static transient final String QUERY_FIND_BY_STRETCH_ID = "RouteStretchLink.findByStretchId";
	public static transient final String QUERY_FIND_STRETCHLIST_BY_ROUTE_ID = "RouteStretchLink.findStretchListByRouteId";
	public static transient final String QUERY_FIND_ROUTELIST_BY_STRETCH_ID = "RouteStretchLink.findRouteListByStretchId";
	public static transient final String QUERY_FIND_BY_ROUTE_ID_AND_STRETCH_ID = "RouteStretchLink.findByRouteIdAndStretchId";	
	public static transient final String QUERY_COUNT_BY_ROUTE_ID = "RouteStretchLink.countByRouteId";
	public static transient final String QUERY_DELETE_BY_ROUTE_ID = "RouteStretchLink.deleteByRouteId";
	public static transient final String QUERY_DELETE_BY_STRETCH_ID = "RouteStretchLink.deleteByStretchId";
	public static transient final String QUERY_DELETE_BY_ROUTE_ID_AND_STRETCH_ID = "RouteStretchLink.deleteByRouteIdAndStretchId";
	
	/**
	 * Query parameters
	 */
	public static transient final String PARAM_ROUTESTRETCHLINK_ID = "routeStretchLinkId";

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "route_id")
	private Route route;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "stretch_id")
	private Stretch stretch;

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Stretch getStretch() {
		return stretch;
	}

	public void setStretch(Stretch stretch) {
		this.stretch = stretch;
	}
	
}
