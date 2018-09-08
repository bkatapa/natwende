package com.mweka.natwende.trip.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.trip.entity.Booking;
import com.mweka.natwende.trip.entity.Trip;
import com.mweka.natwende.trip.vo.BookingVO;

@Stateless
public class BookingDataFacade extends AbstractDataFacade<BookingVO, Booking> {

    public BookingDataFacade() {
        super(BookingVO.class, Booking.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected Booking updateEntity(BookingVO vo) {
        Booking entity = vo.getId() > 0 ? findById(vo.getId()) : new Booking();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
    }

    @Override
    public void convertEntitytoVO(Booking entity, BookingVO vo) {
    	convertBaseEntityToVO(entity, vo);
    	
        vo.setAccountUser(entity.getAccountUser());
        vo.setAccountUserEmail(entity.getAccountUserEmail());
        vo.setBookingStatus(entity.getBookingStatus());
        vo.setCustomerEmail(entity.getCustomerEmail());
        vo.setCustomerName(entity.getCustomerName());
        vo.setFrom(entity.getFrom());
        vo.setTo(entity.getTo());
        vo.setOperatorName(entity.getOperatorName());
        vo.setSeatNumber(entity.getSeatNumber());
        
        if (entity.getTrip() != null) {
        	vo.setTrip(serviceLocator.getTripDataFacade().getCachedVO(entity.getTrip()));
        }
        
        if (entity.getPayment() != null) {
        	vo.setPayment(serviceLocator.getPaymentDataFacade().getCachedVO(entity.getPayment()));
        }
    }

    @Override
    public Booking convertVOToEntity(BookingVO vo, Booking entity) {
        convertBaseVOToEntity(vo, entity);

        entity.setAccountUser(vo.getAccountUser());
        entity.setAccountUserEmail(vo.getAccountUserEmail());
        entity.setBookingStatus(vo.getBookingStatus());
        entity.setCustomerEmail(vo.getCustomerEmail());
        entity.setCustomerName(vo.getCustomerName());
        entity.setFrom(vo.getFrom());
        entity.setTo(vo.getTo());
        entity.setOperatorName(vo.getOperatorName());
        entity.setSeatNumber(vo.getSeatNumber());
        
        if (vo.getTrip() != null) {
        	entity.setTrip(serviceLocator.getTripDataFacade().findById(vo.getTrip().getId()));
        }
        
        if (vo.getPayment() != null) {
        	entity.setPayment(serviceLocator.getPaymentDataFacade().findById(vo.getPayment().getId()));
        }
        return entity;
    }

    @Override
    public BookingVO update(BookingVO vo) {
        Booking entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    public List<BookingVO> getListByTripId(Long tripId) {
    	return transformList(findListByTripId(tripId));
    }
    
    private List<Booking> findListByTripId(Long tripId) {
    	TypedQuery<Booking> query = createNamedQuery(Booking.QUERY_FIND_LIST_BY_TRIP_ID, getEntityClass())
    			.setParameter(Trip.PARAM_TRIP_ID, tripId);
    	return query.getResultList();
    }

}
