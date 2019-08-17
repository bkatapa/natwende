package com.mweka.natwende.trip.facade;

import java.util.List;

import javax.ejb.Stateless;
import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.operator.entity.Operator;
import com.mweka.natwende.operator.entity.Seat;
import com.mweka.natwende.trip.entity.Booking;
import com.mweka.natwende.trip.entity.Reservation;
import com.mweka.natwende.trip.entity.Trip;
import com.mweka.natwende.trip.vo.BookingVO;
import com.mweka.natwende.trip.vo.ReservationVO;
import com.mweka.natwende.types.OperatorName;

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
        vo.setFare(entity.getFare());
        vo.setPassengerAddress(entity.getPassengerAddress());
        vo.setPassengerEmail(entity.getPassengerEmail());
        vo.setPassengerFirstName(entity.getPassengerFirstName());
        vo.setPassengerNrc(entity.getPassengerNrc());
        vo.setPassengerPhoneNumber(entity.getPassengerPhoneNumber());
        vo.setPassengerTitle(entity.getPassengerTitle());
        vo.setBookingStatus(entity.getBookingStatus());
        vo.setFrom(entity.getFrom());
        vo.setTo(entity.getTo());
        vo.setSeatNumber(entity.getSeatNumber());        
        if (entity.getTrip() != null) {
        	vo.setTrip(serviceLocator.getTripDataFacade().getCachedVO(entity.getTrip()));
        }        
        if (entity.getReservation() != null) {
        	vo.setReservation(serviceLocator.getReservationDataFacade().getCachedVO(entity.getReservation()));
        }
    }

    @Override
    public Booking convertVOToEntity(BookingVO vo, Booking entity) {
        convertBaseVOToEntity(vo, entity);
        entity.setBookingStatus(vo.getBookingStatus());
        entity.setFare(vo.getFare());
        entity.setPassengerAddress(vo.getPassengerAddress());
        entity.setPassengerEmail(vo.getPassengerEmail());
        entity.setPassengerFirstName(vo.getPassengerFirstName());
        entity.setPassengerNrc(vo.getPassengerNrc());
        entity.setPassengerPhoneNumber(vo.getPassengerPhoneNumber());
        entity.setPassengerTitle(vo.getPassengerTitle());
        entity.setFrom(vo.getFrom());
        entity.setTo(vo.getTo());
        entity.setSeatNumber(vo.getSeatNumber());        
        if (vo.getTrip() != null) {
        	entity.setTrip(serviceLocator.getTripDataFacade().findById(vo.getTrip().getId()));
        }        
        if (vo.getReservation() != null) {
        	entity.setReservation(serviceLocator.getReservationDataFacade().findById(vo.getReservation().getId()));
        }
        return entity;
    }

    @Override
    public BookingVO update(BookingVO vo) {
        Booking entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    public List<BookingVO> getListByTripId(Long tripId) {
    	List<Booking> resultList = createNamedQuery(Booking.QUERY_FIND_LIST_BY_TRIP_ID, getEntityClass())
    			.setParameter(Trip.PARAM_TRIP_ID, tripId)
    			.getResultList();
    	return transformList(resultList);
    }
    
    public List<BookingVO> getByReservation(ReservationVO reservation) {
    	List<Booking> resultList = createNamedQuery(Booking.QUERY_FIND_LIST_BY_RESERVATION_ID, getEntityClass())
    			.setParameter(Reservation.PARAM_RESERVATION_ID, reservation.getId())
    			.getResultList();
    	return transformList(resultList);
    }
    
    public BookingVO getByTripIdAndSeatNo(Long tripId, String seatNo) {
    	List<Booking> resultList = createNamedQuery(Booking.QUERY_FIND_BY_TRIP_ID_AND_SEAT_NUMBER, getEntityClass())
    			.setParameter(Trip.PARAM_TRIP_ID, tripId)
    			.setParameter(Seat.PARAM_SEAT_NO, seatNo)
    			.getResultList();
    	return getVOFromList(resultList);
    }
    
    public List<BookingVO> getByOperator(OperatorName operatorName) {
    	List<Booking> resultList = createNamedQuery(Booking.QUERY_FIND_LIST_BY_OPERATOR_NAME, getEntityClass())
    			.setParameter(Operator.PARAM_OPERATOR_NAME, operatorName)
    			.getResultList();
    	return transformList(resultList);
    }
    
    public List<BookingVO> getAll() {
    	return transformList(findAll());
    }

}
