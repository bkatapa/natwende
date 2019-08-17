package com.mweka.natwende.trip.vo;

import java.math.BigDecimal;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.types.BookingStatus;
import com.mweka.natwende.types.Title;

public class BookingVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9006165678645743154L;
	
	private String seatNumber;
	private String customerEmail;
	private String from;
	private String to;
	private BigDecimal fare;
	private BookingStatus bookingStatus;
	private String passengerFirstName;
	private String passengerLastName;
	private String passengerEmail;	
	private String passengerPhoneNumber;
	private String passengerAddress;
	private String passengerNrc;
	private Title passengerTitle;
	
	private TripVO trip;
	private ReservationVO reservation;
	
	public BookingVO() {
		fare = BigDecimal.ZERO;
	}
	
	public BookingVO(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	public String getSeatNumber() {
		return seatNumber;
	}
	
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}	
	
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

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	
	public TripVO getTrip() {
		return trip;
	}
	
	public void setTrip(TripVO trip) {
		this.trip = trip;
	}

	public BigDecimal getFare() {
		return fare;
	}

	public void setFare(BigDecimal fare) {
		this.fare = fare;
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

	public Title getPassengerTitle() {
		return passengerTitle;
	}

	public void setPassengerTitle(Title passengerTitle) {
		this.passengerTitle = passengerTitle;
	}

	public ReservationVO getReservation() {
		return reservation;
	}

	public void setReservation(ReservationVO reservation) {
		this.reservation = reservation;
	}
	
	@Override
	public String toString() {
		if (reservation != null && trip != null) {
			return passengerFirstName + " " + passengerLastName + ", " + trip;
		}
		return super.toString();
	}

}
