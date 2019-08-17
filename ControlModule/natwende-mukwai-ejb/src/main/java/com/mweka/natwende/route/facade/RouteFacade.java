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
				//if (serviceLocator.getRouteStopLinkDataFacade().getByRouteIdAndStopId(result.getId(), stop.getId()) == null) {
					serviceLocator.getRouteStopLinkDataFacade().update(new RouteStopLinkVO(stop.getIndex(), result, stop));
				//}
				//else {
					//log.warn("Route/stop link [" + result.getName() + "/" + stop.getName() + "] already exists in the database, hence was not saved.");
				//}
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
			if (serviceLocator.getRouteFacade().fetchByNameStartAndFinalStopStations(route.getName(), route.getStart().getId(), route.getStop().getId()) != null) {
				throw new Exception("Route [" + route.getName() + "] could not be created because another with the same name already exists.");
			}
			return serviceLocator.getRouteDataFacade().update(route);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public RouteVO updateRoute(RouteVO route) throws Exception {
		try {
			return serviceLocator.getRouteDataFacade().update(route);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}

	public void deleteRoute(RouteVO route) throws Exception {
		try {
			//RouteVO routeMirror = serviceLocator.getRouteDataFacade().getByMirrorId(routeId);
			//if (routeMirror != null) {
				//routeMirror.setMirrorRoute(null);			
				//serviceLocator.getRouteFacade().updateRoute(result);
			//}
			serviceLocator.getRouteStopLinkFacade().deleteByRoute(route);
			serviceLocator.getRouteStretchLinkFacade().deleteByRouteId(route.getId());
			serviceLocator.getRouteDataFacade().deleteById(route.getId());
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
	
	public RouteVO fetchByNameStartAndFinalStopStations(String routeName, Long startStationId, Long finalStopId) throws Exception {
		return serviceLocator.getRouteDataFacade().getByNameStartAndFinalStopStations(routeName, startStationId, finalStopId);
	}
	
	public RouteVO fetchById(long routeId) throws Exception {
		return serviceLocator.getRouteDataFacade().getById(routeId);
	}
	
	public RouteVO fetchByMirrorId(long mirrorId) throws Exception {
		return serviceLocator.getRouteDataFacade().getByMirrorId(mirrorId);
	}
	
	public List<RouteVO> fetchRoutesNotYetLinkedToOperator(long operatorId) {
		List<RouteVO> resultList = serviceLocator.getRouteDataFacade().getRoutesNotYetLinkedToOperator(operatorId);
		attachMirrors(resultList);
		return resultList;
	}
	
	public List<RouteVO> fetchRoutesLinkedToOperator(long operatorId) {
		List<RouteVO> resultList = serviceLocator.getRouteDataFacade().getRoutesLinkedToOperator(operatorId);
		attachMirrors(resultList);
		return resultList;
	}
	
	private void attachMirrors(List<RouteVO> routeList) {
		for (RouteVO route : routeList) {
			if (route.getMirrorRoute() != null) {
				continue;
			}
			try {
				RouteVO result = fetchByMirrorId(route.getId());
				if (result != null) {
					route.setMirrorRoute(result);
				}
			}
			catch (Exception ex) {}
		}
			
	}
}
