package com.mweka.natwende.route.facade;

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
	
	public void saveStretchList(List<StretchVO> stretchList) throws Exception {
		for (StretchVO vo : stretchList) {
			if (serviceLocator.getStretchDataFacade().getByEndpointIds(vo.getFrom().getId(), vo.getTo().getId()) == null) {			
				serviceLocator.getStretchFacade().saveStretch(vo);
			}
		}
	}

	public void deleteStretch(Long stretchId) throws Exception {
		try {
			serviceLocator.getStretchDataFacade().deleteById(stretchId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public int deleteStretchesAssociatedWithRouteId(Long routeId) throws Exception {
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
		return serviceLocator.getStretchDataFacade().getListByRouteId(routeId);
	}
	
}