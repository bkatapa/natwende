package com.mweka.natwende.route.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;

@Entity
@Table(name = "Stretch", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"from_id", "to_id"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Stretch.QUERY_FIND_ALL, query=" SELECT s FROM Stretch s "),
    @NamedQuery(name = Stretch.QUERY_FIND_ALL_BY_STATUS, query = " SELECT s FROM Stretch s WHERE s.status = :status "),
    @NamedQuery(name = Stretch.QUERY_FIND_BY_ENDPOINT_IDs, query = " SELECT s FROM Stretch s WHERE s.from.id = :fromId AND s.to.id = :toId "),
})
public class Stretch extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Named queries
	 */
	public static transient final String QUERY_FIND_ALL = "Stretch.findAll";
	public static transient final String QUERY_FIND_ALL_BY_STATUS = "Stretch.findAllByStatus";
	public static transient final String QUERY_FIND_BY_ROUTE_ID = "Stretch.findByRouteId";
	public static transient final String QUERY_FIND_BY_ENDPOINT_IDs = "Stretch.findByEndpointIds";
	public static transient final String QUERY_DELETE_BY_ROUTE_ID = "Stretch.deleteByRouteId";
	
	/**
	 * Query parameters
	 */
	public static transient final String PARAM_STRETCH_ID = "stretchId";
	
	@Temporal(TemporalType.TIME)
	private Date estimatedTravelTime;
	
	@Temporal(TemporalType.TIME)
	private Date observedTravelTime;
	
	@Column(scale = 15, precision = 1)
	private BigDecimal distanceKM;
	
	@Column(scale = 15, precision = 2)
	private BigDecimal fareAmount;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "from_id")
	private Stop from;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "to_id")
	private Stop to;

	public Date getEstimatedTravelTime() {
		return estimatedTravelTime;
	}

	public void setEstimatedTravelTime(Date estimatedTravelTime) {
		this.estimatedTravelTime = estimatedTravelTime;
	}	

	public Date getObservedTravelTime() {
		return observedTravelTime;
	}

	public void setObservedTravelTime(Date observedTravelTime) {
		this.observedTravelTime = observedTravelTime;
	}

	public BigDecimal getDistanceKM() {
		return distanceKM;
	}

	public void setDistanceKM(BigDecimal distanceKM) {
		this.distanceKM = distanceKM;
	}

	public BigDecimal getFareAmount() {
		return fareAmount;
	}

	public void setFareAmount(BigDecimal fareAmount) {
		this.fareAmount = fareAmount;
	}

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
}
