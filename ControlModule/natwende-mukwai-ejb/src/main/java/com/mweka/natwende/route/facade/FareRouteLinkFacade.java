package com.mweka.natwende.route.facade;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.route.vo.FareRouteLinkVO;
import com.mweka.natwende.route.vo.RouteStopLinkVO;

@Stateless
@LocalBean
public class FareRouteLinkFacade extends AbstractFacade<RouteStopLinkVO> {

	public FareRouteLinkFacade() {
		super(FareRouteLinkVO.class);
	}

	public void deleteFareRouteLink(Long linkId) throws Exception {
		try {
			serviceLocator.getFareRouteLinkDataFacade().deleteById(linkId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public List<FareRouteLinkVO> obtainListByRouteId(Long routeId) throws Exception {
		try {
			return serviceLocator.getFareRouteLinkDataFacade().getListByRouteId(routeId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
}
