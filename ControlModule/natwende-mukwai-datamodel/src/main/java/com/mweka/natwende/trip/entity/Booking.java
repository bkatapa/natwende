package com.mweka.natwende.trip.entity;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.payment.entity.Payment;
import com.mweka.natwende.types.BookingStatus;
import com.mweka.natwende.types.OperatorName;

@Entity
@Table(name = "booking", uniqueConstraints = {@UniqueConstraint(columnNames = {"fromTown", "toTown"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Booking.QUERY_FIND_ALL, query=" SELECT b FROM Booking b "),
    @NamedQuery(name = Booking.QUERY_FIND_LIST_BY_TRIP_ID, query = " SELECT b FROM Booking b WHERE b.trip.id = :tripId ")
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
	
	/**
	 * Query parameters
	 */
	public static transient final String PARAM_BOOKING_ID = "bookingId";
	
	@NotNull
	@Column(name = "fromTown", length = 32)
	private String from;
	
	@NotNull
	@Column(name = "toTown", length = 32)
	private String to;
	
	@NotNull
	@Column(length = 10)
	private String seatNumber;
	
	@NotNull
	@Column(length = 50)
	private String accountUser;
	
	@NotNull
	@Column(length = 100)
	private String accountUserEmail;
	
	@NotNull
	@Column(length = 50)
	private String customerName;
	
	@NotNull
	@Column(length = 100)
	private String customerEmail;
	
	@Enumerated(EnumType.STRING)
	private OperatorName operatorName;
	
	@Enumerated(EnumType.STRING)
	private BookingStatus bookingStatus;
	
	@JoinColumn(name = "payment_id", referencedColumnName = "id", nullable = true)
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
	private Payment payment;
	
	@JoinColumn(name = "trip_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Trip trip;
	
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

	public String getAccountUser() {
		return accountUser;
	}

	public void setAccountUser(String accountUser) {
		this.accountUser = accountUser;
	}

	public String getAccountUserEmail() {
		return accountUserEmail;
	}

	public void setAccountUserEmail(String accountUserEmail) {
		this.accountUserEmail = accountUserEmail;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public Payment getPayment() {
		return payment;
	}
	
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	public OperatorName getOperatorName() {
		return operatorName;
	}
	
	public void setOperatorName(OperatorName operatorName) {
		this.operatorName = operatorName;
	}
	
	public Trip getTrip() {
		return trip;
	}
	
	public void setTrip(Trip trip) {
		this.trip = trip;
	}
}
