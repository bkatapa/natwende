package com.mweka.natwende.trip.facade;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.trip.vo.BusTripScheduleLinkVO;
import com.mweka.natwende.trip.vo.TripScheduleVO;

@Stateless
@LocalBean
public class BusTripScheduleLinkFacade extends AbstractFacade<BusTripScheduleLinkVO> {

	public BusTripScheduleLinkFacade() {
		super(BusTripScheduleLinkVO.class);
	}
	
	public BusTripScheduleLinkVO fetchByBusIdAndTripScheduleId(Long busId, Long tripScheduleId) throws Exception {
		try {
			return serviceLocator.getBusTripScheduleLinkDataFacade().getByBusIdAndTripScheduleId(busId, tripScheduleId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public void createLinks(TripScheduleVO tripSchedule) {
		for (BusTripScheduleLinkVO link : tripSchedule.getBusTripScheduleLinkList()) {
			try {
				BusTripScheduleLinkVO result = serviceLocator.getBusTripScheduleLinkDataFacade().getByBusIdAndTripScheduleId(link.getBus().getId(), link.getTripSchedule().getId());
				if (result == null) {
					link.setTripSchedule(tripSchedule);
					serviceLocator.getBusTripScheduleLinkDataFacade().update(link);
				}
			}
			catch (Exception ex) {
				log.debug(ex);
				throw new EJBException(ex);
			}
		}
	}
	
	public void updateBusTripScheduleLinkList(List<BusTripScheduleLinkVO> list) throws Exception {
		for (BusTripScheduleLinkVO link : list) {
			if (link.getDriver() == null) {
				try {
					serviceLocator.getBusTripScheduleLinkDataFacade().deleteById(link.getId());
				}
				catch (Exception e) {
					log.debug(e);
					throw e;
				}
				continue;
			}
			long busId = link.getBus().getId();
			final long tripScheduleId = link.getTripSchedule().getId();
			BusTripScheduleLinkVO result = serviceLocator.getBusTripScheduleLinkDataFacade().getByBusIdAndTripScheduleId(busId, tripScheduleId);
			if (result == null) {
				try {
					serviceLocator.getBusTripScheduleLinkDataFacade().update(link);
				}
				catch (Exception e) {
					log.debug(e);
					throw e;
				}
			}
		}
	}
	
	public List<BusTripScheduleLinkVO> fetchByTripSchedule(TripScheduleVO tripSchedule) throws Exception {
		try {
			return serviceLocator.getBusTripScheduleLinkDataFacade().getListByScheduleId(tripSchedule.getId());
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
 }
