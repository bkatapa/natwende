package com.mweka.natwende.trip.vo;

import java.util.Date;
import java.util.List;

public class ElasticTripVO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6676404862792320257L;

	private List<String> tripSessions, occupiedSeats;
	private Long id;
	private Date insertDate;
	private String status;
	private String uniqueId;
	private Date updateDate;
	private Integer version;
	private Date actualArrivalDate;
	private Date actualDepartureDate;
	private Integer availNumOfSeats;
	private Integer bookedNumOfSeats;
	private String busReg;
	private String driverName;
	private String fromTown;
	private String operatorName;
	private String routeName;
	private Date scheduledArrivalDate;
	private Date scheduledDepartureDate;
	private String toTown;
	private Integer totalNumOfSeats;
	private String travelDurationActual;
	private String travelDurationExpected;
	private String tripStatus;
	private Long tripScheduleId;
	
	public List<String> getTripSessions() {
		return tripSessions;
	}
	
	public void setTripSessions(List<String> tripSessions) {		
		this.tripSessions = tripSessions;
	}
	
	public List<String> getOccupiedSeats() {
		return occupiedSeats;
	}
	
	public void setOccupiedSeats(List<String> occupiedSeats) {
		this.occupiedSeats = occupiedSeats;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getInsertDate() {
		return insertDate;
	}
	
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getUniqueId() {
		return uniqueId;
	}
	
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public Date getActualArrivalDate() {
		return actualArrivalDate;
	}
	
	public void setActualArrivalDate(Date actualArrivalDate) {
		this.actualArrivalDate = actualArrivalDate;
	}
	
	public Date getActualDepartureDate() {
		return actualDepartureDate;
	}
	
	public void setActualDepartureDate(Date actualDepartureDate) {
		this.actualDepartureDate = actualDepartureDate;
	}
	
	public Integer getAvailNumOfSeats() {
		return availNumOfSeats;
	}
	
	public void setAvailNumOfSeats(Integer availNumOfSeats) {
		this.availNumOfSeats = availNumOfSeats;
	}
	
	public Integer getBookedNumOfSeats() {
		return bookedNumOfSeats;
	}
	
	public void setBookedNumOfSeats(Integer bookedNumOfSeats) {
		this.bookedNumOfSeats = bookedNumOfSeats;
	}
	
	public String getBusReg() {
		return busReg;
	}
	
	public void setBusReg(String busReg) {
		this.busReg = busReg;
	}
	
	public String getDriverName() {
		return driverName;
	}
	
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	public String getFromTown() {
		return fromTown;
	}
	
	public void setFromTown(String fromTown) {
		this.fromTown = fromTown;
	}
	
	public String getOperatorName() {
		return operatorName;
	}
	
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public String getRouteName() {
		return routeName;
	}
	
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	
	public Date getScheduledArrivalDate() {
		return scheduledArrivalDate;
	}
	
	public void setScheduledArrivalDate(Date scheduledArrivalDate) {
		this.scheduledArrivalDate = scheduledArrivalDate;
	}
	
	public Date getScheduledDepartureDate() {
		return scheduledDepartureDate;
	}
	
	public void setScheduledDepartureDate(Date scheduledDepartureDate) {
		this.scheduledDepartureDate = scheduledDepartureDate;
	}
	
	public String getToTown() {
		return toTown;
	}
	
	public void setToTown(String toTown) {
		this.toTown = toTown;
	}
	
	public Integer getTotalNumOfSeats() {
		return totalNumOfSeats;
	}
	
	public void setTotalNumOfSeats(Integer totalNumOfSeats) {
		this.totalNumOfSeats = totalNumOfSeats;
	}
	
	public String getTravelDurationActual() {
		return travelDurationActual;
	}
	
	public void setTravelDurationActual(String travelDurationActual) {
		this.travelDurationActual = travelDurationActual;
	}
	
	public String getTravelDurationExpected() {
		return travelDurationExpected;
	}
	
	public void setTravelDurationExpected(String travelDurationExpected) {
		this.travelDurationExpected = travelDurationExpected;
	}
	
	public String getTripStatus() {
		return tripStatus;
	}
	
	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}
	
	public Long getTripScheduleId() {
		return tripScheduleId;
	}
	
	public void setTripScheduleId(Long tripScheduleId) {
		this.tripScheduleId = tripScheduleId;
	}	
	
	public static final String INDEX = "trips";
    public static final String TYPE = "trip";
}
