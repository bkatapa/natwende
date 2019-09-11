package com.mweka.natwende.trip.vo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.route.vo.StretchVO;
import com.mweka.natwende.types.OperatorName;
import com.mweka.natwende.types.Town;
import com.mweka.natwende.types.TripStatus;

public class TripVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2083473637510194261L;

	private String busReg;
	private String routeName;
	private Town from;
	private Town to;
	private String driverName;
	private Date scheduledDepartureDate;
	private Date actualDepartureDate;
	private Date actualDepartureTime;
	private Date scheduledArrivalDate;
	private Date actualArrivalDate;
	private int totalNumOfSeats;
	private int availableNumOfSeats;
	private int bookedNumOfSeats;
	private String travelDurationExpected; // HH:mm
	private String travelDurationActual; // HH:mm
	private TripStatus tripStatus;
	private OperatorName operatorName;
	private Set<String> occupiedSeats;
	
	@XmlTransient
	@JsonIgnore
	private List<BookingVO> bookings;
	
	@XmlTransient
	@JsonIgnore
	private TripScheduleVO tripSchedule;
	
	// No need to persist these
	@XmlTransient
	@JsonIgnore
	private transient String priceStr;
	
	@XmlTransient
	@JsonIgnore
	private transient List<StretchVO> stretchList;
	
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
		if (scheduledDepartureDate != null && tripSchedule != null && tripSchedule.getScheduledDepartureTime() != null) {
			Calendar cTime = Calendar.getInstance(TimeZone.getDefault());
			Calendar cDate = Calendar.getInstance(TimeZone.getDefault());
			cTime.setTime(tripSchedule.getScheduledDepartureTime());
			cDate.setTime(scheduledDepartureDate);
			cDate.set(Calendar.HOUR_OF_DAY, cTime.get(Calendar.HOUR_OF_DAY));
			cDate.set(Calendar.MINUTE, cTime.get(Calendar.MINUTE));
			scheduledDepartureDate = cDate.getTime();
		}
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
	
	public Date getActualDepartureTime() {
		return actualDepartureTime;
	}
	
	public void setActualDepartureTime(Date actualDepartureTime) {
		this.actualDepartureTime = actualDepartureTime;
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
	
	public TripStatus getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(TripStatus tripStatus) {
		this.tripStatus = tripStatus;
	}

	public Set<String> getOccupiedSeats() {
		return occupiedSeats;
	}
	
	public void setOccupiedSeats(Set<String> occupiedSeats) {
		this.occupiedSeats = occupiedSeats;
	}
	
	public List<BookingVO> getBookings() {
		return bookings;
	}
	
	public void setBookings(List<BookingVO> bookings) {
		this.bookings = bookings;
	}

	public TripScheduleVO getTripSchedule() {
		return tripSchedule;
	}

	public void setTripSchedule(TripScheduleVO tripSchedule) {
		this.tripSchedule = tripSchedule;
	}

	public String getPriceStr() {
		return priceStr;
	}

	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}

	public List<StretchVO> getStretchList() {
		return stretchList;
	}

	public void setStretchList(List<StretchVO> stretchList) {
		this.stretchList = stretchList;
	}

	public OperatorName getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(OperatorName operatorName) {
		this.operatorName = operatorName;
	}
	
	public String getScheduledDepartureDateAsString() {
		return new java.text.SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm").format(scheduledDepartureDate);
	}
	
}
