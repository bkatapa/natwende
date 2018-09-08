package com.mweka.natwende.trip.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.route.entity.Route;
import com.mweka.natwende.trip.entity.Trip;
import com.mweka.natwende.trip.vo.TripVO;

@Stateless
public class TripDataFacade extends AbstractDataFacade<TripVO, Trip> {

    public TripDataFacade() {
        super(TripVO.class, Trip.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected Trip updateEntity(TripVO vo) {
        Trip entity = vo.getId() > 0 ? findById(vo.getId()) : new Trip();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
    }

    @Override
    public void convertEntitytoVO(Trip entity, TripVO vo) {
    	convertBaseEntityToVO(entity, vo);
    	
    	vo.setActualArrivalDate(entity.getActualArrivalDate());
    	vo.setActualDepartureDate(entity.getActualDepartureDate());
    	vo.setAvailableNumOfSeats(entity.getAvailableNumOfSeats());
    	vo.setBookedNumOfSeats(entity.getBookedNumOfSeats());
    	vo.setBusReg(entity.getBusReg());
    	vo.setTo(entity.getTo());
    	vo.setFrom(entity.getFrom());
    	vo.setTotalNumOfSeats(entity.getTotalNumOfSeats());
    	vo.setDriverName(entity.getDriverName());
    	vo.setScheduledArrivalDate(entity.getScheduledArrivalDate());
    	vo.setTripStatus(entity.getTripStatus());
    	vo.setTravelDurationActual(entity.getTravelDurationActual());
    	vo.setTravelDurationExpected(entity.getTravelDurationExpected());
    	
    	if (entity.getTripSchedule() != null) {
    		vo.setTripSchedule(serviceLocator.getTripScheduleDataFacade().getCachedVO(entity.getTripSchedule()));
    	}
    }

    @Override
    public Trip convertVOToEntity(TripVO vo, Trip entity) {
        convertBaseVOToEntity(vo, entity);

        entity.setActualArrivalDate(vo.getActualArrivalDate());
    	entity.setActualDepartureDate(vo.getActualDepartureDate());;
    	entity.setAvailableNumOfSeats(vo.getAvailableNumOfSeats());
    	entity.setBookedNumOfSeats(vo.getBookedNumOfSeats());
    	entity.setBusReg(vo.getBusReg());
    	entity.setTo(vo.getTo());
    	entity.setFrom(vo.getFrom());
    	entity.setTotalNumOfSeats(vo.getTotalNumOfSeats());
    	entity.setDriverName(vo.getDriverName());
    	entity.setScheduledArrivalDate(vo.getScheduledArrivalDate());
    	entity.setTripStatus(vo.getTripStatus());
    	entity.setTravelDurationActual(vo.getTravelDurationActual());
    	entity.setTravelDurationExpected(vo.getTravelDurationExpected());
    	
    	if (vo.getTripSchedule() != null) {
    		entity.setTripSchedule(serviceLocator.getTripScheduleDataFacade().findById(vo.getTripSchedule().getId()));
    	}
        
        return entity;
    }

    @Override
    public TripVO update(TripVO vo) {
        Trip entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    public List<TripVO> getListByRouteName(String routeName) {
    	return transformList(findListByRouteName(routeName));
    }
    
    private List<Trip> findListByRouteName(String routeName) {
    	TypedQuery<Trip> query = createNamedQuery(Trip.QUERY_FIND_LIST_BY_ROUTE_NAME, getEntityClass())
    			.setParameter(Route.PARAM_ROUTE_NAME, routeName);
    	return query.getResultList();
    }

}
