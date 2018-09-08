package com.mweka.natwende.route.facade;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.route.vo.RouteStopLinkVO;
import com.mweka.natwende.route.vo.RouteVO;
import com.mweka.natwende.route.vo.StopVO;
import com.mweka.natwende.types.Status;

@Stateless
@LocalBean
public class RouteFacade extends AbstractFacade<RouteVO> {

	public RouteFacade() {
		super(RouteVO.class);
	}
	
	public RouteVO saveRouteAndStationLinks(RouteVO route, List<StopVO> stopList) throws Exception {
		try {
			RouteVO result = serviceLocator.getRouteFacade().saveRoute(route);
			for (StopVO stop : stopList) {
				if (serviceLocator.getRouteStopLinkDataFacade().getByRouteIdAndStopId(result.getId(), stop.getId()) == null) {
					serviceLocator.getRouteStopLinkDataFacade().update(new RouteStopLinkVO(result, stop));
				}
				else {
					log.warn("Route/stop link [" + result.getName() + "/" + stop.getName() + "] already exists in the database, hence was not saved.");
				}
			}
			return result;
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public RouteVO saveRoute(RouteVO route) throws Exception {
		try {
			if (serviceLocator.getRouteDataFacade().getByNameStartAndFinalStopStations(route.getName(), route.getStart().getId(), route.getStop().getId()) != null) {
				throw new Exception("Route [" + route.getName() + "] could not be created because another with the same name already exists.");
			}
			return serviceLocator.getRouteDataFacade().update(route);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}

	public void deleteRoute(Long routeId) throws Exception {
		try {
			serviceLocator.getRouteDataFacade().deleteById(routeId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public List<RouteVO> obtainAllByStatus(Status status) throws Exception {
		try {
			return serviceLocator.getRouteDataFacade().getAllByStatus(status);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
}
