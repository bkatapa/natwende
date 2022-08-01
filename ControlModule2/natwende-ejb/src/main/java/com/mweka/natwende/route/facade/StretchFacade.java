package com.mweka.natwende.route.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.route.vo.StopVO;
import com.mweka.natwende.route.vo.StretchVO;
import com.mweka.natwende.types.Town;

@Stateless
@LocalBean
public class StretchFacade extends AbstractFacade<StretchVO> {

	public StretchFacade() {
		super(StretchVO.class);
	}
	
	public StretchVO saveStretch(StretchVO vo) throws Exception {
		try {
			return serviceLocator.getStretchDataFacade().update(vo);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public List<StretchVO> saveStretchList(List<StretchVO> stretchList) throws Exception {
		List<StretchVO> resultList = new ArrayList<>();
		for (StretchVO s : stretchList) {
			if (s.getId() == -1L) {
				resultList.add(serviceLocator.getStretchDataFacade().update(s));
			}
		}
		return resultList;
	}

	public void deleteStretch(Long stretchId) throws Exception {
		try {
			serviceLocator.getRouteStretchLinkFacade().deleteByStretchId(stretchId);
			serviceLocator.getStretchDataFacade().deleteById(stretchId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public long deleteStretchesAssociatedWithRouteId(Long routeId) throws Exception {
		try {
			return serviceLocator.getRouteStretchLinkDataFacade().deleteByRouteId(routeId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public StretchVO getStretchById(Long stretchId) throws Exception {
		try {
			return serviceLocator.getStretchDataFacade().getById(stretchId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public StretchVO getStretchByEndpoints(StopVO origin, StopVO destination) throws Exception {
		return serviceLocator.getStretchDataFacade().getByEndpointIds(origin.getId(), destination.getId());
	}
	
	public StretchVO getStretchByEndpoints(Long fromId, Long toId) throws Exception {
		try {
			return serviceLocator.getStretchDataFacade().getByEndpointIds(fromId, toId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public StretchVO getStretchByEndpoints(Town fromTown, Town toTown) throws Exception {
		try {
			List<StopVO> resultList = serviceLocator.getStopFacade().obtainStopByTown(fromTown);
			StopVO from = null;
			StopVO to = null;
			
			if (resultList.isEmpty()) {
				log.debug("StopVO for departure town was not found");
				return null;
			}
			else {
				from = resultList.get(0);
			}
			
			resultList = serviceLocator.getStopFacade().obtainStopByTown(toTown);
			
			if (resultList.isEmpty()) {
				log.debug("StopVO for destination town was not found");
				return null;
			}
			else {
				to = resultList.get(0);
			}
			
			StretchVO result = serviceLocator.getStretchDataFacade().getByEndpointIds(from.getId(), to.getId());
			
			if (result == null) {
				log.debug("StetchVO for search towns departure [" + fromTown + "], destination [" + toTown + "], was not found");
			}
			
			return result;
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public List<StretchVO> obtainStretchListGivenRouteId(Long routeId) {
		return routeId == null ? serviceLocator.getStretchDataFacade().getAll() : serviceLocator.getStretchDataFacade().getListByRouteId(routeId);
	}
	
}