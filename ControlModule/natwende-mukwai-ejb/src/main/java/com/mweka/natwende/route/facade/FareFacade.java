package com.mweka.natwende.route.facade;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.route.vo.FareVO;

@Stateless
@LocalBean
public class FareFacade extends AbstractFacade<FareVO> {

	public FareFacade() {
		super(FareVO.class);
	}

	public void deleteFare(Long fareId) throws Exception {
		try {
			serviceLocator.getFareDataFacade().deleteById(fareId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public List<FareVO> obtainListByRouteId(Long routeId) throws Exception {
		try {
			return serviceLocator.getFareDataFacade().getListByRouteId(routeId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
}
