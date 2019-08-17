package com.mweka.natwende.trip.facade;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.trip.vo.TripScheduleVO;
import com.mweka.natwende.types.OperatorName;
import com.mweka.natwende.types.Status;

@Stateless
@LocalBean
public class TripScheduleFacade  extends AbstractFacade<TripScheduleVO> {

	public TripScheduleFacade() {
		super(TripScheduleVO.class);
	}
	
	public TripScheduleVO save(TripScheduleVO tripSchedule) {
		try {
			return serviceLocator.getTripScheduleDataFacade().update(tripSchedule);
		} catch (EntityNotFoundException e) {
			log.debug(e);
			throw new EJBException(e);
		}
	}
	
	public List<TripScheduleVO> getActiveSchedules(OperatorName operatorName) {
		List<TripScheduleVO> resultList = serviceLocator.getTripScheduleDataFacade().getListByOperatorNameAndStatus(operatorName, Status.ACTIVE);
		try {
			for (TripScheduleVO tripSchedule : resultList) {
				tripSchedule.setBusTripScheduleLinkList(serviceLocator.getBusTripScheduleLinkFacade().fetchByTripSchedule(tripSchedule));
			}
		}
		catch (Exception ex) {
			throw new EJBException(ex);
		}
		return resultList;
	}
	
}
