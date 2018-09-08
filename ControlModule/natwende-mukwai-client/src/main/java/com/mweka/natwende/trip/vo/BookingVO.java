package com.mweka.natwende.trip.vo;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.payment.vo.PaymentVO;
import com.mweka.natwende.types.BookingStatus;
import com.mweka.natwende.types.OperatorName;

public class BookingVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9006165678645743154L;
	
	private String seatNumber;
	private String accountUser;
	private String accountUserEmail;
	private String customerName;
	private String customerEmail;
	private String from;
	private String to;
	private BookingStatus bookingStatus;
	private OperatorName operatorName;
	private TripVO trip;
	private PaymentVO payment;
	
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

	public OperatorName getOperatorName() {
		return operatorName;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public void setOperatorName(OperatorName operatorName) {
		this.operatorName = operatorName;
	}

	public PaymentVO getPayment() {
		return payment;
	}
	
	public void setPayment(PaymentVO payment) {
		this.payment = payment;
	}
	
	public TripVO getTrip() {
		return trip;
	}
	
	public void setTrip(TripVO trip) {
		this.trip = trip;
	}

}
