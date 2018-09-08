package com.mweka.natwende.route.facade;

import java.util.List;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.route.entity.Route;
import com.mweka.natwende.route.entity.RouteStretchLink;
import com.mweka.natwende.route.entity.Stretch;
import com.mweka.natwende.route.vo.RouteStretchLinkVO;

public class RouteStretchLinkDataFacade extends AbstractDataFacade<RouteStretchLinkVO, RouteStretchLink> {

    public RouteStretchLinkDataFacade() {
        super(RouteStretchLinkVO.class, RouteStretchLink.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected RouteStretchLink updateEntity(RouteStretchLinkVO vo) {
    	RouteStretchLink entity = vo.getId() > 0 ? findById(vo.getId()) : new RouteStretchLink();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
    }

    @Override
    public void convertEntitytoVO(RouteStretchLink entity, RouteStretchLinkVO vo) {
    	convertBaseEntityToVO(entity, vo);
    	
    	if (entity.getRoute() != null) {
    		vo.setRoute(serviceLocator.getRouteDataFacade().getCachedVO(entity.getRoute()));
    	}
    	if (entity.getStretch() != null) {
    		vo.setStretch(serviceLocator.getStretchDataFacade().getCachedVO(entity.getStretch()));
    	}
    }

    @Override
    public RouteStretchLink convertVOToEntity(RouteStretchLinkVO vo, RouteStretchLink entity) {
        convertBaseVOToEntity(vo, entity);
    	
    	if (vo.getRoute() != null) {
    		entity.setRoute(serviceLocator.getRouteDataFacade().findById(vo.getRoute().getId()));
    	}
    	if (vo.getStretch() != null) {
    		entity.setStretch(serviceLocator.getStretchDataFacade().findById(vo.getStretch().getId()));
    	}
        return entity;
    }

    @Override
    public RouteStretchLinkVO update(RouteStretchLinkVO vo) {
    	RouteStretchLink entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    public RouteStretchLinkVO getByRouteIdAndStretchId(Long routeId, Long stretchId) {
		List<RouteStretchLink> resultList = createNamedQuery(RouteStretchLink.QUERY_FIND_BY_ROUTE_ID_AND_STRETCH_ID, getEntityClass())
				.setParameter(Route.PARAM_ROUTE_ID, routeId)
				.setParameter(Stretch.PARAM_STRETCH_ID, stretchId)				
				.getResultList();
		return getVOFromList(resultList);
	}    
	
	public int deleteByRouteId(Long routeId) throws Exception {
		return getEntityManager().createQuery(RouteStretchLink.QUERY_DELETE_BY_ROUTE_ID).setParameter(Route.PARAM_ROUTE_ID, routeId).executeUpdate();
	}
	
	public int deleteByStretchId(Long stretchId) throws Exception {
		return getEntityManager().createQuery(RouteStretchLink.QUERY_DELETE_BY_STRETCH_ID).setParameter(Stretch.PARAM_STRETCH_ID, stretchId).executeUpdate();
	}
	
	public int deleteByRouteIdAndStretchId(Long routeId, Long stretchId) throws Exception {
		return getEntityManager().createQuery(RouteStretchLink.QUERY_DELETE_BY_ROUTE_ID_AND_STRETCH_ID)
				.setParameter(Route.PARAM_ROUTE_ID, routeId)
				.setParameter(Stretch.PARAM_STRETCH_ID, stretchId)
				.executeUpdate();
	}
}