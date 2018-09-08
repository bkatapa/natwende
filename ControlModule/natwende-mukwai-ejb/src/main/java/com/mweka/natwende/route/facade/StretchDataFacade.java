package com.mweka.natwende.route.facade;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.commons.logging.LogFactory;
import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.route.entity.Route;
import com.mweka.natwende.route.entity.RouteStretchLink;
import com.mweka.natwende.route.entity.Stretch;
import com.mweka.natwende.route.entity.Stop;
import com.mweka.natwende.route.vo.StretchVO;

@Stateless
public class StretchDataFacade extends AbstractDataFacade<StretchVO, Stretch> {

    public StretchDataFacade() {
        super(StretchVO.class, Stretch.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected Stretch updateEntity(StretchVO vo) {
        Stretch entity = vo.getId() > 0 ? findById(vo.getId()) : new Stretch();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
    }

    @Override
    public void convertEntitytoVO(Stretch entity, StretchVO vo) {
    	convertBaseEntityToVO(entity, vo);    	
    	vo.setEstimatedTravelTime(entity.getEstimatedTravelTime());
    	vo.setObservedTravelTime(entity.getObservedTravelTime());
    	vo.setDistanceKM(entity.getDistanceKM());
    	vo.setFareAmount(entity.getFareAmount());
    	
    	if (entity.getFrom() != null) {
    		vo.setFrom(serviceLocator.getStopDataFacade().getCachedVO(entity.getFrom()));
    	}
    	if (entity.getTo() != null) {
    		vo.setTo(serviceLocator.getStopDataFacade().getCachedVO(entity.getTo()));
    	}
    }

    @Override
    public Stretch convertVOToEntity(StretchVO vo, Stretch entity) {
        convertBaseVOToEntity(vo, entity);
        entity.setEstimatedTravelTime(vo.getEstimatedTravelTime());
        entity.setObservedTravelTime(vo.getObservedTravelTime());
    	entity.setDistanceKM(vo.getDistanceKM());
    	entity.setFareAmount(vo.getFareAmount());
    	
    	if (vo.getFrom() != null) {
    		entity.setFrom(serviceLocator.getStopDataFacade().findById(vo.getFrom().getId()));
    	}
    	if (vo.getTo() != null) {
    		entity.setTo(serviceLocator.getStopDataFacade().findById(vo.getTo().getId()));
    	}
        return entity;
    }

    @Override
    public StretchVO update(StretchVO vo) {
        Stretch entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    public StretchVO getByEndpointIds(Long fromId, Long toId) {
		List<Stretch> resultList = createNamedQuery(Stretch.QUERY_FIND_BY_ENDPOINT_IDs, getEntityClass())
				.setParameter(Stop.PARAM_FROM_ID, fromId)
				.setParameter(Stop.PARAM_TO_ID, toId)
				.getResultList();
		return getVOFromList(resultList);
	}    
	
	public int deleteByRouteId(Long routeId) throws Exception {
		return getEntityManager().createQuery(Stretch.QUERY_DELETE_BY_ROUTE_ID).setParameter(Route.PARAM_ROUTE_ID, routeId).executeUpdate();
	}
	
	public List<StretchVO> getListByRouteId(Long routeId) {
		return transformList(findListByRouteId(routeId));
	}
	
	private List<Stretch> findListByRouteId(Long routeId) {
		List<Stretch> resultList = createNamedQuery(RouteStretchLink.QUERY_FIND_STRETCHLIST_BY_ROUTE_ID, getEntityClass())
				.setParameter(Route.PARAM_ROUTE_ID, routeId)
				.getResultList();
		return resultList;
	}
}