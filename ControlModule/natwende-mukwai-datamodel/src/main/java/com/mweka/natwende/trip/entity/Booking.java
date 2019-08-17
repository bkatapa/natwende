package com.mweka.natwende.trip.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.types.BookingStatus;
import com.mweka.natwende.types.Title;

@Entity
@Table(name = "booking")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Booking.QUERY_FIND_ALL, query=" SELECT b FROM Booking b "),
    @NamedQuery(name = Booking.QUERY_FIND_LIST_BY_TRIP_ID, query = " SELECT b FROM Booking b WHERE b.trip.id = :tripId "),
    @NamedQuery(name = Booking.QUERY_FIND_LIST_BY_RESERVATION_ID, query = " SELECT b FROM Booking b WHERE b.reservation.id = :reservationId "),
    @NamedQuery(name = Booking.QUERY_FIND_LIST_BY_OPERATOR_NAME, query = " SELECT b FROM Booking b WHERE b.trip.operatorName = :operatorName "),
    @NamedQuery(name = Booking.QUERY_FIND_BY_TRIP_ID_AND_SEAT_NUMBER, query = " SELECT b FROM Booking b WHERE b.trip.id = :tripId AND b.seatNumber = :seatNo ")
})
public class Booking extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8098313004866153380L;
	
	/**
	 * Named queries
	 */
	public static transient final String QUERY_FIND_ALL = "Booking.findAll";
	public static transient final String QUERY_FIND_LIST_BY_TRIP_ID = "Booking.findListByTripId";
	public static transient final String QUERY_FIND_LIST_BY_RESERVATION_ID = "Booking.findListByReservationId";
	public static transient final String QUERY_FIND_LIST_BY_OPERATOR_NAME = "Booking.findListByOperatorName";
	public static transient final String QUERY_FIND_BY_TRIP_ID_AND_SEAT_NUMBER = "Booking.findByTripIdAndSeatNumber";
	
	/**
	 * Query parameters
	 */
	public static transient final String PARAM_BOOKING_ID = "bookingId";
	
	@NotNull
	@Column(name = "from_town", length = 32)
	private String from;
	
	@NotNull
	@Column(name = "to_town", length = 32)
	private String to;
	
	@NotNull
	@Column(name = "seat_no", length = 10)
	private String seatNumber;
	
	@NotNull
	@Column(name = "passenger_first_name", length = 100)
	private String passengerFirstName;
	
	@Column(name = "passenger_last_name", length = 100)
	private String passengerLastName;
	
	@Column(name = "passenger_email", length = 100)
	private String passengerEmail;
	
	@Column(name = "passenger_phone_number", length = 100)
	private String passengerPhoneNumber;
	
	@Column(name = "passenger_address", length = 100)
	private String passengerAddress;
	
	@Column(name = "passenger_nrc", length = 50)
	private String passengerNrc;
	
	@Column(name = "bus_fare")
	private BigDecimal fare;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "passenger_title", length = 50)
	private Title passengerTitle;
	
	@Enumerated(EnumType.STRING)
	private BookingStatus bookingStatus;
	
	@JoinColumn(name = "trip_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Trip trip;
	
	@JoinColumn(name = "reservation_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Reservation reservation;
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	
	public Trip getTrip() {
		return trip;
	}
	
	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public String getPassengerFirstName() {
		return passengerFirstName;
	}

	public void setPassengerFirstName(String passengerFirstName) {
		this.passengerFirstName = passengerFirstName;
	}

	public String getPassengerLastName() {
		return passengerLastName;
	}

	public void setPassengerLastName(String passengerLastName) {
		this.passengerLastName = passengerLastName;
	}

	public String getPassengerEmail() {
		return passengerEmail;
	}

	public void setPassengerEmail(String passengerEmail) {
		this.passengerEmail = passengerEmail;
	}

	public String getPassengerPhoneNumber() {
		return passengerPhoneNumber;
	}

	public void setPassengerPhoneNumber(String passengerPhoneNumber) {
		this.passengerPhoneNumber = passengerPhoneNumber;
	}

	public String getPassengerAddress() {
		return passengerAddress;
	}

	public void setPassengerAddress(String passengerAddress) {
		this.passengerAddress = passengerAddress;
	}

	public String getPassengerNrc() {
		return passengerNrc;
	}

	public void setPassengerNrc(String passengerNrc) {
		this.passengerNrc = passengerNrc;
	}

	public BigDecimal getFare() {
		return fare;
	}

	public void setFare(BigDecimal fare) {
		this.fare = fare;
	}

	public Title getPassengerTitle() {
		return passengerTitle;
	}

	public void setPassengerTitle(Title passengerTitle) {
		this.passengerTitle = passengerTitle;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	
}
