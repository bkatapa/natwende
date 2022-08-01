package com.mweka.natwende.trip.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.operator.entity.Operator;
import com.mweka.natwende.route.entity.Stop;
import com.mweka.natwende.route.vo.StretchVO;
import com.mweka.natwende.trip.entity.TripSchedule;
import com.mweka.natwende.trip.vo.TripScheduleVO;
import com.mweka.natwende.types.OperatorName;
import com.mweka.natwende.types.Status;

@Stateless
public class TripScheduleDataFacade extends AbstractDataFacade<TripScheduleVO, TripSchedule> {

    public TripScheduleDataFacade() {
        super(TripScheduleVO.class, TripSchedule.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

	@Override
	protected void convertEntitytoVO(TripSchedule entity, TripScheduleVO vo) {
		super.convertBaseEntityToVO(entity, vo);
		vo.setEndDate(entity.getEndDate());
		vo.setFrequency(entity.getFrequency());
		vo.setScheduledDepartureTime(entity.getScheduledDepartureTime());
		vo.setStartDate(entity.getStartDate());
		
		if (entity.getRoute() != null) {
			vo.setRoute(serviceLocator.getRouteDataFacade().getCachedVO(entity.getRoute()));
		}
		
		if (entity.getOperator() != null) {
			vo.setOperator(serviceLocator.getOperatorDataFacade().getCachedVO(entity.getOperator()));
		}
	}

	@Override
	protected TripSchedule convertVOToEntity(TripScheduleVO vo, TripSchedule entity) {
		super.convertBaseVOToEntity(vo, entity);
		entity.setEndDate(vo.getEndDate());
		entity.setFrequency(vo.getFrequency());
		entity.setScheduledDepartureTime(vo.getScheduledDepartureTime());
		entity.setStartDate(vo.getStartDate());
		
		if (vo.getRoute() != null) {
			entity.setRoute(serviceLocator.getRouteDataFacade().findById(vo.getRoute().getId()));
		}
		
		if (vo.getOperator() != null) {
			entity.setOperator(serviceLocator.getOperatorDataFacade().findById(vo.getOperator().getId()));
		}
		return entity;
	}

	@Override
	protected TripSchedule updateEntity(TripScheduleVO vo) throws EntityNotFoundException {
		TripSchedule entity = vo.getId() > 0 ? findById(vo.getId()) : new TripSchedule();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
	}
	
	public List<TripScheduleVO> getListByOperatorNameAndStatus(OperatorName operatorName, Status status) {
		List<TripSchedule> resultList = createNamedQuery(TripSchedule.QUERY_FIND_LIST_BY_OPERATORNAME_AND_STATUS, getEntityClass())
				.setParameter(Operator.PARAM_OPERATOR_NAME, operatorName)
				.setParameter(TripSchedule.PARAM_STATUS, status)
				.getResultList();
		
		return transformList(resultList);
	}
	
	public List<TripScheduleVO> getListByRouteStretchAndTravelDate(StretchVO stretch, Date travelDate) {
		List<TripSchedule> resultList = createNamedQuery(TripSchedule.QUERY_FIND_LIST_BY_ROUTE_STRETCH_AND_TRAVELDATE, getEntityClass())
				.setParameter(Stop.PARAM_FROM_TOWN, stretch.getFrom().getTown())
				.setParameter(Stop.PARAM_TO_TOWN, stretch.getTo().getTown())
				.getResultList();
		return transformList(resultList);
	}
}
