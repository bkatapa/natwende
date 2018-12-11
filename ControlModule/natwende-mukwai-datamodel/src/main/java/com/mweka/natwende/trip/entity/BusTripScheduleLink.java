package com.mweka.natwende.trip.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.operator.entity.Bus;
import com.mweka.natwende.user.entity.User;

@Entity
@Table(name = "BusTripScheduleLink", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"bus_id", "tripSchedule_Id", "driver_id"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = BusTripScheduleLink.QUERY_FIND_ALL, query=" SELECT btsl FROM BusTripScheduleLink btsl "),
    @NamedQuery(name = BusTripScheduleLink.QUERY_FIND_BY_TRIPSCHEDULE_ID, query = " SELECT btsl FROM BusTripScheduleLink btsl WHERE btsl.tripSchedule.id = :tripScheduleId "),
    @NamedQuery(name = BusTripScheduleLink.QUERY_FIND_BY_BUS_ID_AND_TRIPSCHEDULE_ID, query = " SELECT btsl FROM BusTripScheduleLink btsl WHERE btsl.bus.id = :busId AND btsl.tripSchedule.id = :tripScheduleId ")
})
public class BusTripScheduleLink extends BaseEntity {
	
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 5082471514312217197L;

	/**
	 * Named queries
	 */
	public static transient final String QUERY_FIND_ALL = "BusTripScheduleLink.findAll";
	public static transient final String QUERY_FIND_BY_BUS_ID_AND_TRIPSCHEDULE_ID = "BusTripScheduleLink.findByBusIdAndTripScheduleId";
	public static transient final String QUERY_FIND_BY_TRIPSCHEDULE_ID = "BusTripScheduleLink.findByTripScheduleId";
	
	/**
	 * Query parameters
	 */
	public static transient final String PARAM_BUS_TRIPSCHEDULE_LINK_ID = "busTripScheduleLinkId";

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "bus_id", referencedColumnName = "id", nullable = false)
	private Bus bus;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "tripSchedule_id", referencedColumnName = "id", nullable = false)
	private TripSchedule tripSchedule;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = true)
	private User driver;

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public TripSchedule getTripSchedule() {
		return tripSchedule;
	}

	public void setTripSchedule(TripSchedule tripSchedule) {
		this.tripSchedule = tripSchedule;
	}

	public User getDriver() {
		return driver;
	}

	public void setDriver(User driver) {
		this.driver = driver;
	}	
	
}
