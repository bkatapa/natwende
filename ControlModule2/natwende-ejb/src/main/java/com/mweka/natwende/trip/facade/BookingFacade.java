package com.mweka.natwende.trip.facade;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.trip.vo.BookingVO;

@Stateless
@LocalBean
public class BookingFacade extends AbstractFacade<BookingVO> {

	public BookingFacade() {
		super(BookingVO.class);
	}

	public void deleteBooking(Long bookingId) throws Exception {
		try {
			serviceLocator.getBookingDataFacade().deleteById(bookingId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public List<BookingVO> obtainListByTripId(Long tripId) throws Exception {
		try {
			return serviceLocator.getBookingDataFacade().getListByTripId(tripId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
}
