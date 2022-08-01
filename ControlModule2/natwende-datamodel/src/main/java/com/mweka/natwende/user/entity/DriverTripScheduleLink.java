package com.mweka.natwende.user.entity;

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
import com.mweka.natwende.trip.entity.TripSchedule;

@Entity
@Table(name = "DriverTripScheduleLink", uniqueConstraints = {@UniqueConstraint(columnNames = {"driver_id", "tripSchedule_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = DriverTripScheduleLink.QUERY_FIND_ALL, query=" SELECT dtl FROM DriverTripScheduleLink dtl "),
    @NamedQuery(name = DriverTripScheduleLink.QUERY_FIND_BY_DRIVER_ID_AND_TRIPSCHEDULE_ID, query = " SELECT dtl FROM DriverTripScheduleLink dtl WHERE dtl.driver.id = :driverId AND dtl.tripSchedule.id = :tripScheduleId ")
})
public class DriverTripScheduleLink extends BaseEntity {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -6571882282887973991L;
	
	/**
	 * Named queries
	 */
	public static transient final String QUERY_FIND_ALL = "DriverTripScheduleLink.findAll";
	public static transient final String QUERY_FIND_BY_DRIVER_ID_AND_TRIPSCHEDULE_ID = "DriverTripScheduleLink.findByDriverIdAndTripScheduleId";	
	
	/**
	 * Query parameters
	 */
	public static transient final String PARAM_DRIVERTRIPLINK_ID = "driverTripLinkId";
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
	private User driver;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "tripSchedule_id", referencedColumnName = "id", nullable = false)
	private TripSchedule tripSchedule;

	public User getDriver() {
		return driver;
	}

	public void setDriver(User driver) {
		this.driver = driver;
	}

	public TripSchedule getTripSchedule() {
		return tripSchedule;
	}

	public void setTripSchedule(TripSchedule tripSchedule) {
		this.tripSchedule = tripSchedule;
	}
	
}
