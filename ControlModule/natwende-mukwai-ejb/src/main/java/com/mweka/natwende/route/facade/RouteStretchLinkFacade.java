package com.mweka.natwende.route.facade;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.route.vo.RouteStretchLinkVO;

@Stateless
@LocalBean
public class RouteStretchLinkFacade extends AbstractFacade<RouteStretchLinkVO> {

	public RouteStretchLinkFacade() {
		super(RouteStretchLinkVO.class);
	}
	
	public RouteStretchLinkVO saveRouteStretchLink(RouteStretchLinkVO vo) throws Exception {
		try {
			return serviceLocator.getRouteStretchLinkDataFacade().update(vo);
		}
		catch (Exception e) {
			log.debug(e);
			throw new Exception(e);
		}
	}
	
	public RouteStretchLinkVO obtainByRouteIdAndStretchId(Long routeId, Long stretchId) {
		return serviceLocator.getRouteStretchLinkDataFacade().getByRouteIdAndStretchId(routeId, stretchId);
	}
	
	public List<RouteStretchLinkVO> obtainByStretchId(Long stretchId) {
		return serviceLocator.getRouteStretchLinkDataFacade().getByStretchId(stretchId);
	}
	
	public int deleteByRouteId(Long routeId) throws Exception {
		try {
			return serviceLocator.getRouteStretchLinkDataFacade().deleteByRouteId(routeId);
		}
		catch (Exception e) {
			log.debug(e);
			throw new Exception(e);
		}
	}
}
