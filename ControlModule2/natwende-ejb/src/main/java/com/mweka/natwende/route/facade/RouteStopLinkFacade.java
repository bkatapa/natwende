package com.mweka.natwende.route.facade;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.route.vo.RouteStopLinkVO;
import com.mweka.natwende.route.vo.RouteVO;
import com.mweka.natwende.types.Status;

@Stateless
@LocalBean
public class RouteStopLinkFacade extends AbstractFacade<RouteStopLinkVO> {

	public RouteStopLinkFacade() {
		super(RouteStopLinkVO.class);
	}
	
	public RouteStopLinkVO saveRouteStopLink(RouteStopLinkVO routeStopLink) throws Exception {
		try {
			return serviceLocator.getRouteStopLinkDataFacade().update(routeStopLink);			
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public void saveRouteStopLinkList(List<RouteStopLinkVO> routeStopLinkList, RouteVO route) throws Exception {
		for (RouteStopLinkVO link : routeStopLinkList) {
			link.setRoute(route);
			RouteStopLinkVO result = serviceLocator.getRouteStopLinkFacade().obtainByRouteIdAndStopId(link.getRoute().getId(), link.getStop().getId());
			if (result == null) {
				serviceLocator.getRouteStopLinkFacade().saveRouteStopLink(link);
			}
		}
	}

	public void deleteRouteStopLink(Long linkId) throws Exception {
		try {
			serviceLocator.getRouteStopLinkDataFacade().deleteById(linkId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public List<RouteStopLinkVO> obtainAllByStatus(Status status) throws Exception {
		try {
			return serviceLocator.getRouteStopLinkDataFacade().getAllByStatus(status);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public RouteStopLinkVO obtainByRouteIdAndStopId(Long routeId, Long stopId) throws Exception {
		try {
			return serviceLocator.getRouteStopLinkDataFacade().getByRouteIdAndStopId(routeId, stopId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long deleteByRoute(RouteVO route) throws Exception {
		return serviceLocator.getRouteStopLinkDataFacade().deleteByRoute(route);
	}
}
