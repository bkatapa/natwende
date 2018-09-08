package com.mweka.natwende.trip.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.operator.entity.Operator;
import com.mweka.natwende.route.entity.Route;
import com.mweka.natwende.types.DaysOfWeek;

@Entity
@Table(name = "TripSchedule", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"operator_id", "route_id", "startDate", "endDate", "scheduledDepartureTime"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = TripSchedule.QUERY_FIND_ALL, query=" SELECT t FROM Trip t "),
    @NamedQuery(name = TripSchedule.QUERY_FIND_LIST_BY_ROUTE_ID, query = " SELECT t FROM TripSchedule t WHERE t.route.id = :routeId "),
    @NamedQuery(name = TripSchedule.QUERY_FIND_LIST_BY_OPERATORNAME_AND_STATUS, query = " SELECT ts FROM TripSchedule ts WHERE ts.operator.name = :operatorName AND ts.status = :status ")
})
public class TripSchedule extends BaseEntity {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -7999157731498064735L;

	/**
	 * Named queries
	 */
	public static transient final String QUERY_FIND_ALL = "TripSchedule.findAll";
	public static transient final String QUERY_FIND_LIST_BY_ROUTE_ID = "TripSchedule.findListByRouteId";
	public static transient final String QUERY_FIND_LIST_BY_OPERATORNAME_AND_STATUS = "TripSchedule.findListByOperatorNameAndStatus";
	
	/**
	 * Query parameters
	 */
	public static transient final String PARAM_TRIPSCHEDULE_ID = "tripScheduleId";
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@Temporal(TemporalType.TIME)
	private Date scheduledDepartureTime;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "operator_id")
	private Operator operator;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "route_id")
	private Route route;
	
	@ElementCollection(targetClass = DaysOfWeek.class)
	@CollectionTable(name = "DaysOfWeek", joinColumns = @JoinColumn(name = "tripSchedule_id"))
	@Column(name = "dayOfWeek", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<DaysOfWeek> frequency;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tripSchedule")
	private List<BusTripScheduleLink> busTripScheduleLinkList;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getScheduledDepartureTime() {
		return scheduledDepartureTime;
	}

	public void setScheduledDepartureTime(Date scheduledDepartureTime) {
		this.scheduledDepartureTime = scheduledDepartureTime;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public List<DaysOfWeek> getFrequency() {
		return frequency;
	}

	public void setFrequency(List<DaysOfWeek> frequency) {
		this.frequency = frequency;
	}

	public List<BusTripScheduleLink> getBusTripScheduleLinkList() {
		return busTripScheduleLinkList;
	}

	public void setBusTripScheduleLinkList(List<BusTripScheduleLink> busTripScheduleLinkList) {
		this.busTripScheduleLinkList = busTripScheduleLinkList;
	}
	
}
