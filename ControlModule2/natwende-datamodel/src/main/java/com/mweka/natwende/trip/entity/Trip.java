package com.mweka.natwende.trip.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.types.OperatorName;
import com.mweka.natwende.types.Town;
import com.mweka.natwende.types.TripStatus;

@Entity
@Table(name = "Trip", uniqueConstraints = {@UniqueConstraint(columnNames = {"bus_reg", "from_town", "to_town", "scheduled_departure_date"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Trip.QUERY_FIND_ALL, query=" SELECT t FROM Trip t "),
    @NamedQuery(name = Trip.QUERY_FIND_ACTIVE, query = " SELECT t FROM Trip t WHERE t.scheduledDepartureDate > CURRENT_DATE "),
    @NamedQuery(name = Trip.QUERY_FIND_LIST_BY_ROUTE_NAME, query = " SELECT t FROM Trip t WHERE t.routeName = :routeName "),
    @NamedQuery(name = Trip.QUERY_FIND_ACTIVE_BY_STRETCH_AND_TRAVEL_DATE, query = " SELECT t FROM Trip t, RouteStretchLink rsl WHERE t.tripSchedule.route.id = rsl.route.id AND t.scheduledDepartureDate > CURRENT_DATE AND t.scheduledDepartureDate BETWEEN :date1 AND :date2 AND rsl.stretch.from.town = :fromTown AND rsl.stretch.to.town = :toTown "),
    @NamedQuery(name = Trip.QUERY_FIND_BY_BUS_ID_AND_DEPARTURE_DATETIME, query = " SELECT t FROM Trip t WHERE t.busReg = :busReg AND t.scheduledDepartureDate = :departureDate AND t.tripSchedule.scheduledDepartureTime = :departureTime "),
    @NamedQuery(name = Trip.QUERY_FIND_BY_ROUTE_ID_AND_DEPARTURE_DATETIME, query = " SELECT t FROM Trip t WHERE t.tripSchedule.route.id = :routeId AND t.scheduledDepartureDate > CURRENT_DATE AND t.scheduledDepartureDate BETWEEN :date1 AND :date2 ")
})
public class Trip extends BaseEntity {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -8075669714263573829L;
	
	/**
	 * Named queries
	 */
	public static transient final String QUERY_FIND_ALL = "Trip.findAll";
	public static transient final String QUERY_FIND_ACTIVE = "Trip.findActive";
	public static transient final String QUERY_FIND_LIST_BY_ROUTE_NAME = "Trip.findListByRouteName";
	public static transient final String QUERY_FIND_ACTIVE_BY_STRETCH_AND_TRAVEL_DATE = "Trip.findActiveByStretchAndTravelDate";
	public static transient final String QUERY_FIND_BY_BUS_ID_AND_DEPARTURE_DATETIME = "Trip.findByBusIdAndDepartureDateTime";
	public static transient final String QUERY_FIND_BY_ROUTE_ID_AND_DEPARTURE_DATETIME = "Trip.findByRouteIdAndDepartureDateTime";
	
	/**
	 * Query parameters
	 */
	public static transient final String PARAM_TRIP_ID = "tripId";
	
	@Column(name = "total_num_of_seats")
	private int totalNumOfSeats;
	
	@Column(name = "avail_num_of_seats")
	private int availableNumOfSeats;
	
	@Column(name = "booked_num_of_seats")
	private int bookedNumOfSeats;
	
	@NotNull
    @Column(name = "from_town", length = 100)	
	private Town from;
	
	@NotNull
    @Column(name = "to_town", length = 100)
	private Town to;	
	
    @Column(name = "driver_name", length = 100)
	private String driverName;	
	
	@Column(name = "bus_reg", length = 32)
	private String busReg;
	
	@NotNull
	@Column(name = "route_name", length = 100)
	private String routeName;
	
	@Size(max = 255)
    @Column(name = "travel_duration_expected", length = 255)
	private String travelDurationExpected; // HH:mm
	
	@Size(max = 255)
    @Column(name = "travel_duration_actual", length = 255)
	private String travelDurationActual; // HH:mm
	
	@Enumerated(EnumType.STRING)
	@Column(name = "trip_status")
	private TripStatus tripStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "operator_name")
	private OperatorName operatorName;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "scheduled_departure_date")
	private Date scheduledDepartureDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "actual_departure_date")
	private Date actualDepartureDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "scheduled_arrival_date")
	private Date scheduledArrivalDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "actual_arrival_date")
	private Date actualArrivalDate;		
	
	@OneToMany(mappedBy = "trip", fetch = FetchType.LAZY)
	private List<Booking> bookings;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.ALL})
	@JoinColumn(name = "trip_schedule_id")
	private TripSchedule tripSchedule;
	
	public String getBusReg() {
		return busReg;
	}
	
	public void setBusReg(String busReg) {
		this.busReg = busReg;
	}
	
	public String getRouteName() {
		return routeName;
	}
	
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	
	public Town getFrom() {
		return from;
	}
	
	public void setFrom(Town from) {
		this.from = from;
	}
	
	public Town getTo() {
		return to;
	}
	
	public void setTo(Town to) {
		this.to = to;
	}
	
	public String getDriverName() {
		return driverName;
	}
	
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	public Date getScheduledDepartureDate() {
		return scheduledDepartureDate;
	}
	
	public void setScheduledDepartureDate(Date scheduledDepartureDate) {
		this.scheduledDepartureDate = scheduledDepartureDate;
	}
	
	public Date getActualDepartureDate() {
		return actualDepartureDate;
	}
	
	public void setActualDepartureDate(Date actualDepartureDate) {
		this.actualDepartureDate = actualDepartureDate;
	}
	
	public Date getScheduledArrivalDate() {
		return scheduledArrivalDate;
	}
	
	public void setScheduledArrivalDate(Date scheduledArrivalDate) {
		this.scheduledArrivalDate = scheduledArrivalDate;
	}
	
	public Date getActualArrivalDate() {
		return actualArrivalDate;
	}
	
	public void setActualArrivalDate(Date actualArrivalDate) {
		this.actualArrivalDate = actualArrivalDate;
	}
	
	public int getTotalNumOfSeats() {
		return totalNumOfSeats;
	}
	
	public void setTotalNumOfSeats(int totalNumOfSeats) {
		this.totalNumOfSeats = totalNumOfSeats;
	}
	
	public int getAvailableNumOfSeats() {
		return availableNumOfSeats;
	}
	
	public void setAvailableNumOfSeats(int availableNumOfSeats) {
		this.availableNumOfSeats = availableNumOfSeats;
	}
	
	public int getBookedNumOfSeats() {
		return bookedNumOfSeats;
	}
	
	public void setBookedNumOfSeats(int bookedNumOfSeats) {
		this.bookedNumOfSeats = bookedNumOfSeats;
	}
	
	public String getTravelDurationExpected() {
		return travelDurationExpected;
	}
	
	public void setTravelDurationExpected(String travelDurationExpected) {
		this.travelDurationExpected = travelDurationExpected;
	}
	
	public String getTravelDurationActual() {
		return travelDurationActual;
	}
	
	public void setTravelDurationActual(String travelDurationActual) {
		this.travelDurationActual = travelDurationActual;
	}	
	
	public List<Booking> getBookings() {
		return bookings;
	}
	
	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public TripStatus getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(TripStatus tripStatus) {
		this.tripStatus = tripStatus;
	}

	public TripSchedule getTripSchedule() {
		return tripSchedule;
	}

	public void setTripSchedule(TripSchedule tripSchedule) {
		this.tripSchedule = tripSchedule;
	}

	public OperatorName getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(OperatorName operatorName) {
		this.operatorName = operatorName;
	}	
	
}
