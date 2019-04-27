package com.mweka.natwende.trip.vo;

import java.util.List;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.payment.vo.PaymentVO;
import com.mweka.natwende.types.BookingStatus;
import com.mweka.natwende.user.vo.UserVO;

public class ReservationVO extends BaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1935457123775130207L;

	private UserVO customer;
	private PaymentVO payment;
	private BookingStatus bookingStatus;
	private List<BookingVO> bookingList;
	
	public UserVO getCustomer() {
		return customer;
	}
	
	public void setCustomer(UserVO customer) {
		this.customer = customer;
	}
	
	public PaymentVO getPayment() {
		return payment;
	}
	
	public void setPayment(PaymentVO payment) {
		this.payment = payment;
	}
	
	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}
	
	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	
	public List<BookingVO> getBookingList() {
		return bookingList;
	}
	
	public void setBookingList(List<BookingVO> bookingList) {
		this.bookingList = bookingList;
	}	
	
}
