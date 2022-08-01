package com.mweka.natwende.trip.vo;

import java.io.Serializable;

import com.mweka.natwende.types.BookingStatus;

public class BookingVOBuilder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1877797801043468817L;

	private BookingVO booking;
	
	public BookingVOBuilder() {
		booking = new BookingVO();
	}
	
	public BookingVOBuilder(String seatNo) {
		this();
		booking.setSeatNumber(seatNo);
	}
	
	public BookingVOBuilder seatNo(String seatNo) {
		booking.setSeatNumber(seatNo);
		return this;
	}
	
	public BookingVOBuilder seatCoordinate(String coordinate) {
		booking.setSeatCoordinate(coordinate);
		return this;
	}
	
	public BookingVOBuilder fromTown(String fromTown) {
		booking.setFrom(fromTown);
		return this;
	}
	
	public BookingVOBuilder toTown(String toTown) {
		booking.setTo(toTown);
		return this;
	}
	
	public BookingVOBuilder seatFare(java.math.BigDecimal fare) {
		booking.setFare(fare);
		return this;
	}
	
	public BookingVOBuilder customerEmail(String email) {
		booking.setCustomerEmail(email);
		return this;
	}
	
	public BookingVOBuilder bookingStatus(BookingStatus bookingStatus) {
		booking.setBookingStatus(bookingStatus);
		return this;
	}
	
	public BookingVO build() {
		return booking;
	}
}
