package com.mweka.natwende.route.facade;

import java.util.List;

import javax.ejb.Stateless;
import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.operator.entity.Operator;
import com.mweka.natwende.route.entity.Route;
import com.mweka.natwende.route.entity.RouteStretchLink;
import com.mweka.natwende.route.entity.Stop;
import com.mweka.natwende.route.entity.Stretch;
import com.mweka.natwende.route.vo.RouteVO;
import com.mweka.natwende.route.vo.StretchVO;
import com.mweka.natwende.types.Status;

@Stateless
public class RouteDataFacade extends AbstractDataFacade<RouteVO, Route> {

    public RouteDataFacade() {
        super(RouteVO.class, Route.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected Route updateEntity(RouteVO vo) {
        Route entity = vo.getId() > 0 ? findById(vo.getId()) : new Route();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
    }

    @Override
    public void convertEntitytoVO(Route entity, RouteVO vo) {
    	convertBaseEntityToVO(entity, vo);    	
    	vo.setName(entity.getName());
    	
    	if (entity.getStart() != null) {
    		vo.setStart(serviceLocator.getStopDataFacade().getCachedVO(entity.getStart()));
    	}
    	if (entity.getStop() != null) {
    		vo.setStop(serviceLocator.getStopDataFacade().getCachedVO(entity.getStop()));
    	}
    	if (entity.getMirrorRoute() != null) {
    		//vo.setMirrorRoute(getCachedVO(entity.getMirrorRoute()));
    	}
    }

    @Override
    public Route convertVOToEntity(RouteVO vo, Route entity) {
        convertBaseVOToEntity(vo, entity);
        entity.setName(vo.getName());
    	
    	if (vo.getStart() != null) {
    		entity.setStart(serviceLocator.getStopDataFacade().findById(vo.getStart().getId()));
    	}
    	if (vo.getStop() != null) {
    		entity.setStop(serviceLocator.getStopDataFacade().findById(vo.getStop().getId()));
    	}
    	if (vo.getMirrorRoute() != null) {
    		entity.setMirrorRoute(findById(vo.getMirrorRoute().getId()));
    	}
        return entity;
    }

    @Override
    public RouteVO update(RouteVO vo) {
        Route entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    public RouteVO getByName(String routeName) {
    	List<Route> resultList = createNamedQuery(Route.QUERY_FIND_BY_NAME, getEntityClass())
				.setParameter(Route.PARAM_ROUTE_NAME, routeName)
				.getResultList();
		return getVOFromList(resultList);
	}
    
    public RouteVO getByMirrorRoute(RouteVO mirrorRoute) {
    	return getByMirrorId(mirrorRoute.getId());
    }
    
    public RouteVO getByMirrorId(long mirrorId) {
    	List<Route> resultList = createNamedQuery(Route.QUERY_FIND_BY_MIRROR_ID, getEntityClass())
				.setParameter(Route.PARAM_ROUTE_ID, mirrorId)
				.getResultList();
		return getVOFromList(resultList);
	}
    
    public RouteVO getByNameStartAndFinalStopStations(String routeName, Long startId, Long finalStopId) {
    	List<Route> resultList = createNamedQuery(Route.QUERY_FIND_BY_NAME_START_AND_FINAL_STOP_STATION_IDs, getEntityClass())
				.setParameter(Route.PARAM_ROUTE_NAME, routeName)
				.setParameter(Stop.PARAM_START_ID, startId)
				.setParameter(Stop.PARAM_FINAL_STOP_ID, finalStopId)
				.getResultList();
		return getVOFromList(resultList);
	}

    public List<RouteVO> getAllByStatus(Status status) {
    	final List<Route> resultList = createNamedQuery(Route.QUERY_FIND_ALL_BY_STATUS, getEntityClass())
				.setParameter(BaseEntity.PARAM_STATUS, status)
				.getResultList();
    	return transformList(resultList);
    }
	
	public List<RouteVO> getByStretch(StretchVO stretch) {
		List<Route> resultList = createNamedQuery(RouteStretchLink.QUERY_FIND_ROUTELIST_BY_STRETCH_ID, getEntityClass())
				.setParameter(Stretch.PARAM_STRETCH_ID, stretch.getId())
				.getResultList();
		return transformList(resultList);
	}
	
	public List<RouteVO> getRoutesNotYetLinkedToOperator(long operatorId) {
		List<Route> resultList = createNamedQuery(Route.QUERY_FIND_NOT_YET_LINKED_TO_OPERATOR, getEntityClass())
				.setParameter(Operator.PARAM_OPERATOR_ID, operatorId)
				.getResultList();
		return transformList(resultList);
	}
	
	public List<RouteVO> getRoutesLinkedToOperator(long operatorId) {
		List<Route> resultList = createNamedQuery(Route.QUERY_FIND_ROUTES_LINKED_TO_OPERATOR, getEntityClass())
				.setParameter(Operator.PARAM_OPERATOR_ID, operatorId)
				.getResultList();
		return transformList(resultList);
	}
}
