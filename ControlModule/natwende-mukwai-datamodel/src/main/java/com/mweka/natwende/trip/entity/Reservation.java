package com.mweka.natwende.trip.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.payment.entity.Payment;
import com.mweka.natwende.types.BookingStatus;
import com.mweka.natwende.user.entity.User;

@Entity
@Table(name = "reservation", uniqueConstraints = {@UniqueConstraint(columnNames = {"customer_id", "payment_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Reservation.QUERY_FIND_ALL, query=" SELECT r FROM Reservation r "),
    @NamedQuery(name = Reservation.QUERY_FIND_BY_PAYMENT_REF, query = " SELECT r FROM Reservation r WHERE r.payment.ref = :paymentRef "),
    @NamedQuery(name = Reservation.QUERY_FIND_BY_CUSTOMER_ID, query = " SELECT r FROM Reservation r WHERE r.customer.id = :userId ")
})
public class Reservation extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4358584426973134026L;
	
	/**
	 * Named queries
	 */
	public static transient final String QUERY_FIND_ALL = "Reservation.findAll";
	public static transient final String QUERY_FIND_BY_CUSTOMER_ID = "Reservation.findByCustomerId";
	public static transient final String QUERY_FIND_BY_PAYMENT_REF = "Reservation.findByPaymentRef";
	
	/**
	 * Query parameters
	 */
	public static transient final String PARAM_RESERVATION_ID = "reservationId";

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private User customer;
	
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_id")
	private Payment payment;
	
	private BookingStatus bookingStatus;

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}	
	
}
