package com.mweka.natwende.trip.facade;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.trip.vo.TripVO;

@Stateless
@LocalBean
public class TripFacade extends AbstractFacade<TripVO> {

	public TripFacade() {
		super(TripVO.class);
	}

	public void deleteTrip(Long tripId) throws Exception {
		try {
			serviceLocator.getTripDataFacade().deleteById(tripId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public List<TripVO> obtainListByRouteName(String routeName) throws Exception {
		try {
			return serviceLocator.getTripDataFacade().getListByRouteName(routeName);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
}
