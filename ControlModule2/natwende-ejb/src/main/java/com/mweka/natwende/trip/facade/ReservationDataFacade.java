package com.mweka.natwende.trip.facade;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.payment.entity.Payment;
import com.mweka.natwende.trip.entity.Reservation;
import com.mweka.natwende.trip.vo.ReservationVO;
import com.mweka.natwende.user.entity.User;
import com.mweka.natwende.user.vo.UserVO;

@Stateless
public class ReservationDataFacade extends AbstractDataFacade<ReservationVO, Reservation> {

    public ReservationDataFacade() {
        super(ReservationVO.class, Reservation.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected Reservation updateEntity(ReservationVO vo) {
    	Reservation entity = vo.getId() > 0 ? findById(vo.getId()) : new Reservation();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
    }

    @Override
    public void convertEntitytoVO(Reservation entity, ReservationVO vo) {
    	super.convertBaseEntityToVO(entity, vo);    	
    	vo.setBookingStatus(entity.getBookingStatus());
    	
    	if (entity.getCustomer() != null) {
    		vo.setCustomer(serviceLocator.getUserDataFacade().getCachedVO(entity.getCustomer()));
    	}
    	if (entity.getPayment() != null) {
    		vo.setPayment(serviceLocator.getPaymentDataFacade().getCachedVO(entity.getPayment()));
    	}
    }

    @Override
    public Reservation convertVOToEntity(ReservationVO vo, Reservation entity) {
        super.convertBaseVOToEntity(vo, entity);
        entity.setBookingStatus(vo.getBookingStatus());
    	
    	if (vo.getCustomer() != null) {
    		entity.setCustomer(serviceLocator.getUserDataFacade().findById(vo.getCustomer().getId()));
    	}
    	if (vo.getPayment() != null) {
    		entity.setPayment(serviceLocator.getPaymentDataFacade().findById(vo.getPayment().getId()));
    	}
        
        return entity;
    }

    @Override
    public ReservationVO update(ReservationVO vo) {
    	Reservation entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    public List<ReservationVO> getListByCustomer(UserVO customer) {
    	List<Reservation> resultList = createNamedQuery(Reservation.QUERY_FIND_BY_CUSTOMER_ID, getEntityClass())
			.setParameter(User.PARAM_USER_ID, customer.getId())
			.getResultList();
    	return transformList(resultList);
    }

    public ReservationVO getByPaymentRef(String paymentRef) {
    	List<Reservation> resultList = createNamedQuery(Reservation.QUERY_FIND_BY_PAYMENT_REF, getEntityClass())
			.setParameter(Payment.PARAM_REF, paymentRef)
			.getResultList();    	
    	return getVOFromList(resultList);
    }
    
}
