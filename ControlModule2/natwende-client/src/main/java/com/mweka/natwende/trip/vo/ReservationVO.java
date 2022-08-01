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
		if (payment == null) {
			payment = new PaymentVO();
		}
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
	
	public String getReference() {
		if (payment != null && payment.getRef() != null && !payment.getRef().trim().isEmpty()) {
			return payment.getRef();
		}
		return "";
	}
	
	public static java.util.Set<String> getReservedCoordinates(java.util.Collection<String> bookingList) {
		java.util.Set<String> resultSet = new java.util.HashSet<>();
		for (String booking : bookingList) {
			resultSet.add(booking.split("\\|")[1]);
		}
		return resultSet;
	}
	
	public static String[] getEndpoints(java.util.Collection<String> bookingList) {
		if (bookingList != null) {
			for (String booking : bookingList) {
				return new String[]{booking.split("\\|")[2], booking.split("\\|")[3]};
			}
		}
		return new String[0];
	}
	
}
