package com.mweka.natwende.route.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.types.Province;
import com.mweka.natwende.types.Town;

@Entity
@Table(name = "Stop")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Stop.QUERY_FIND_ALL, query="SELECT s FROM Stop s"),
    @NamedQuery(name = Stop.QUERY_FIND_ALL_BY_STATUS, query="SELECT s FROM Stop s WHERE s.status = :status"),
    @NamedQuery(name = Stop.QUERY_FIND_STOP_BY_NAME, query="SELECT s FROM Stop s WHERE s.name = :stopName"),
    @NamedQuery(name = Stop.QUERY_FIND_BY_NAME_TOWN_AND_PROVINCE, query="SELECT s FROM Stop s WHERE s.name = :stopName AND s.town = :town AND s.province = :province"),
})
public class Stop extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3540078236922894773L;
	
	/**
	 * Named queries
	 */
	public static final String QUERY_FIND_ALL = "Stop.findAll";
	public static final String QUERY_FIND_ALL_BY_STATUS = "Stop.findAllByStatus";
	public static final String QUERY_FIND_STOP_BY_NAME = "Stop.findStopByName";
	public static final String QUERY_FIND_BY_NAME_TOWN_AND_PROVINCE = "Stop.findByNameTownAndProvince";
	
	/**
	 * Query parameters
	 */
	public static final String PARAM_STOP_ID = "stopId";
	public static final String PARAM_START_ID = "startId";
	public static final String PARAM_FINAL_STOP_ID = "finalStopId";
	public static final String PARAM_STOP_NAME = "stopName";
	public static final String PARAM_TOWN = "town";
	public static final String PARAM_PROVINE = "province";
	public static final String PARAM_FROM_ID = "fromId";
	public static final String PARAM_TO_ID = "toId";

	@NotNull(message = "station name cannot be empty.")
	@NotEmpty(message = "station name cannot be empty.")
	@Size(max = 100, message = "sation name was too long.")
	private String name;
	
	@Enumerated(EnumType.STRING)
    @Column(length = 32)
	private Town town;
	
	@Enumerated(EnumType.STRING)
    @Column(length = 32)
	private Province province;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "stop", fetch = FetchType.LAZY)
    private List<RouteStopLink> routeStops;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Town getTown() {
		return town;
	}
	
	public void setTown(Town town) {
		this.town = town;
	}
	
	public Province getProvince() {
		return province;
	}
	
	public void setProvince(Province province) {
		this.province = province;
	}

	public List<RouteStopLink> getRouteStops() {
		return routeStops;
	}

	public void setRouteStops(List<RouteStopLink> routeStops) {
		this.routeStops = routeStops;
	}
	
}
