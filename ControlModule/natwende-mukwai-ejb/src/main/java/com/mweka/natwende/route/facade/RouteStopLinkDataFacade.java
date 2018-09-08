package com.mweka.natwende.route.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.route.entity.Route;
import com.mweka.natwende.route.entity.RouteStopLink;
import com.mweka.natwende.route.entity.Stop;
import com.mweka.natwende.route.vo.RouteStopLinkVO;
import com.mweka.natwende.types.Status;

@Stateless
public class RouteStopLinkDataFacade extends AbstractDataFacade<RouteStopLinkVO, RouteStopLink> {

    public RouteStopLinkDataFacade() {
        super(RouteStopLinkVO.class, RouteStopLink.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected RouteStopLink updateEntity(RouteStopLinkVO vo) {
        RouteStopLink entity = vo.getId() > 0 ? findById(vo.getId()) : new RouteStopLink();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
    }

    @Override
    public void convertEntitytoVO(RouteStopLink entity, RouteStopLinkVO vo) {
        convertBaseEntityToVO(entity, vo);
        vo.setStationIndex(entity.getStationIndex());
        
        if (entity.getRoute() != null) {
        	vo.setRoute(serviceLocator.getRouteDataFacade().getCachedVO(entity.getRoute()));
        }
        if (entity.getStop() != null) {
        	vo.setStop(serviceLocator.getStopDataFacade().getCachedVO(entity.getStop()));
        }
    }

    @Override
    public RouteStopLink convertVOToEntity(RouteStopLinkVO vo, RouteStopLink entity) {
        convertBaseVOToEntity(vo, entity);
        entity.setStationIndex(vo.getStationIndex());

        if (vo.getRoute() != null) {
        	entity.setRoute(serviceLocator.getRouteDataFacade().findById(vo.getRoute().getId()));
        }
        if (vo.getStop() != null) {
        	entity.setStop(serviceLocator.getStopDataFacade().findById(vo.getStop().getId()));
        }
        
        return entity;
    }

    @Override
    public RouteStopLinkVO update(RouteStopLinkVO vo) {
        RouteStopLink entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    public List<RouteStopLinkVO> getAllByStatus(Status status) {
		return transformList(findAllByStatus(status));
	}
	
	private List<RouteStopLink> findAllByStatus(Status status) {
		final TypedQuery<RouteStopLink> query = createNamedQuery(RouteStopLink.QUERY_FIND_ALL_BY_STATUS, getEntityClass())
				.setParameter(BaseEntity.PARAM_STATUS, status);
		return query.getResultList();
	}
	
	public List<RouteStopLinkVO> getAllByRouteId(Long routeId) {
		return transformList(findAllByRouteId(routeId));
	}
	
	private List<RouteStopLink> findAllByRouteId(Long routeId) {
		final TypedQuery<RouteStopLink> query = createNamedQuery(RouteStopLink.QUERY_FIND_ALL_BY_ROUTE_ID, getEntityClass())
				.setParameter(Route.PARAM_ROUTE_ID, routeId);
		return query.getResultList();
	}
	
	public RouteStopLinkVO getByRouteIdAndStopId(Long routeId, Long stopId) {
		List<RouteStopLink> resultList = createNamedQuery(RouteStopLink.QUERY_FIND_BY_ROUTE_ID_AND_STOP_ID, getEntityClass())
				.setParameter(Route.PARAM_ROUTE_ID, routeId)
				.setParameter(Stop.PARAM_STOP_ID, stopId)
				.getResultList();
		return getVOFromList(resultList);
	}

}
