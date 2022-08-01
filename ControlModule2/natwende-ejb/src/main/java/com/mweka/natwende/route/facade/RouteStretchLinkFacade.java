package com.mweka.natwende.route.facade;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.route.vo.RouteStretchLinkVO;
import com.mweka.natwende.route.vo.RouteVO;
import com.mweka.natwende.route.vo.StretchVO;

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
	
	public void createNewRouteStretchLinkList(RouteVO route, List<StretchVO> stretchList) throws Exception {
		for (StretchVO stretch : stretchList) {
			StretchVO stretchFromDB = serviceLocator.getStretchFacade().getStretchByEndpoints(stretch.getFrom().getId(), stretch.getTo().getId());
			if (stretchFromDB == null) {
				stretch.setId(-1L);
				stretch.setUniqueId(stretch.getUniqueId() + "x");
				stretch = serviceLocator.getStretchFacade().saveStretch(stretch);
			}
			else {
				stretch = stretchFromDB;
			}
			createNewRouteStretchLink(route, stretch);
		}
	}
	
	public void createNewRouteStretchLink(RouteVO route, StretchVO stretch) throws Exception {		
		RouteStretchLinkVO result = serviceLocator.getRouteStretchLinkFacade().obtainByRouteIdAndStretchId(route.getId(), stretch.getId());
		if (result == null) {
			serviceLocator.getRouteStretchLinkFacade().saveRouteStretchLink(new RouteStretchLinkVO(route, stretch));
		}
	}
	
	public RouteStretchLinkVO obtainByRouteIdAndStretchId(Long routeId, Long stretchId) {
		return serviceLocator.getRouteStretchLinkDataFacade().getByRouteIdAndStretchId(routeId, stretchId);
	}
	
	public List<RouteStretchLinkVO> obtainByRouteId(Long routeId) {
		return serviceLocator.getRouteStretchLinkDataFacade().getByRouteId(routeId);
	}
	
	public List<RouteStretchLinkVO> obtainByStretchId(Long stretchId) {
		return serviceLocator.getRouteStretchLinkDataFacade().getByStretchId(stretchId);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long deleteByRouteId(Long routeId) throws Exception {
		try {
			return serviceLocator.getRouteStretchLinkDataFacade().deleteByRouteId(routeId);
		}
		catch (Exception e) {
			log.debug(e);
			throw new Exception(e);
		}
	}
	
	public int deleteByStretchId(Long stretchId) throws Exception {
		try {
			return serviceLocator.getRouteStretchLinkDataFacade().deleteByStretchId(stretchId);
		}
		catch (Exception e) {
			log.debug(e);
			throw new Exception(e);
		}
	}
}
