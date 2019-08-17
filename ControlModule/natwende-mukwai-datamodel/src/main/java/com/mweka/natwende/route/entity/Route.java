package com.mweka.natwende.route.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;

@Entity
@Table(name = "route", uniqueConstraints = {@UniqueConstraint(columnNames = {"start_id", "final_stop_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Route.QUERY_FIND_ALL, query="SELECT r FROM Route r"),
    @NamedQuery(name = Route.QUERY_FIND_ALL_BY_STATUS, query = "SELECT r FROM Route r where r.status = :status"),
    @NamedQuery(name = Route.QUERY_FIND_BY_NAME, query = "SELECT r FROM Route r where r.name = :routeName"),
    @NamedQuery(name = Route.QUERY_FIND_BY_MIRROR_ID, query = "SELECT r FROM Route r where r.mirrorRoute.id = :routeId"),
    @NamedQuery(name = Route.QUERY_FIND_NOT_YET_LINKED_TO_OPERATOR, query = " SELECT r FROM Route r WHERE r.id NOT IN (SELECT orl.route.id FROM OperatorRouteLink orl WHERE orl.operator.id = :operatorId) "),
    @NamedQuery(name = Route.QUERY_FIND_ROUTES_LINKED_TO_OPERATOR, query = " SELECT orl.route FROM OperatorRouteLink orl WHERE orl.operator.id = :operatorId "),
    @NamedQuery(name = Route.QUERY_FIND_BY_NAME_START_AND_FINAL_STOP_STATION_IDs, query = "SELECT r FROM Route r where r.name = :routeName AND r.start.id = :startId AND r.stop.id = :finalStopId")
})
public class Route extends BaseEntity {

	/**
	 * 
	 */
	private static transient final long serialVersionUID = -2382515942847322018L;
	
	/**
	 * Named queries
	 */
	public static transient final String QUERY_FIND_ALL = "Route.findAll";
	public static transient final String QUERY_FIND_ALL_BY_STATUS = "Route.findAllByStatus";
	public static transient final String QUERY_FIND_BY_NAME = "Route.findByName";
	public static transient final String QUERY_FIND_BY_MIRROR_ID = "Route.findByMirrorId";
	public static transient final String QUERY_FIND_BY_NAME_START_AND_FINAL_STOP_STATION_IDs = "Route.findByNameStartAndFinalStopIds";
	public static transient final String QUERY_FIND_NOT_YET_LINKED_TO_OPERATOR = "Route.findNotYetLinkedToOperator";
	public static transient final String QUERY_FIND_ROUTES_LINKED_TO_OPERATOR = "Route.findRoutesLinkedToOperator";
	
	/**
	 * Query parameters
	 */
	public static transient final String PARAM_ROUTE_ID = "routeId";
	public static transient final String PARAM_ROUTE_NAME = "routeName";
	
	private String name;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "start_id")
	private Stop start;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "final_stop_id")
	private Stop stop;
	
	@OneToOne(optional = true, cascade = {CascadeType.ALL})
	@JoinColumn(name = "mirror_route_id")
	private Route mirrorRoute;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "route", fetch = FetchType.LAZY)    
	private List<RouteStopLink> routeStops;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Stop getStart() {
		return start;
	}
	
	public void setStart(Stop start) {
		this.start = start;
	}
	
	public Stop getStop() {
		return stop;
	}
	
	public void setStop(Stop stop) {
		this.stop = stop;
	}
	
	public Route getMirrorRoute() {
		return mirrorRoute;
	}

	public void setMirrorRoute(Route mirrorRoute) {
		this.mirrorRoute = mirrorRoute;
	}

	public List<RouteStopLink> getRouteStops() {
		return routeStops;
	}
	
	public void setStops(List<RouteStopLink> routeStops) {
		this.routeStops = routeStops;
	}

}
