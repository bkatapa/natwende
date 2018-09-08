package com.mweka.natwende.route.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.route.entity.FareRouteLink;
import com.mweka.natwende.route.entity.Route;
import com.mweka.natwende.route.vo.FareRouteLinkVO;
import com.mweka.natwende.types.Status;

@Stateless
public class FareRouteLinkDataFacade extends AbstractDataFacade<FareRouteLinkVO, FareRouteLink> {

    public FareRouteLinkDataFacade() {
        super(FareRouteLinkVO.class, FareRouteLink.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected FareRouteLink updateEntity(FareRouteLinkVO vo) {
    	FareRouteLink entity = vo.getId() > 0 ? findById(vo.getId()) : new FareRouteLink();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
    }

    @Override
    public void convertEntitytoVO(FareRouteLink entity, FareRouteLinkVO vo) {
        convertBaseEntityToVO(entity, vo);
        
        if (entity.getFare() != null) {
        	
        }
        if (entity.getRoute() != null) {
        	
        }
    }

    @Override
    public FareRouteLink convertVOToEntity(FareRouteLinkVO vo, FareRouteLink entity) {
        convertBaseVOToEntity(vo, entity);

        if (vo.getFare() != null) {
        	
        }
        if (vo.getRoute() != null) {
        	
        }
        return entity;
    }

    @Override
    public FareRouteLinkVO update(FareRouteLinkVO vo) {
    	FareRouteLink entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    public List<FareRouteLinkVO> getAllByStatus(Status status) {
		return transformList(findAllByStatus(status));
	}
	
	private List<FareRouteLink> findAllByStatus(Status status) {
		final TypedQuery<FareRouteLink> query = createNamedQuery(FareRouteLink.QUERY_FIND_ALL_BY_STATUS, getEntityClass())
				.setParameter(FareRouteLink.PARAM_STATUS, status);
		return query.getResultList();
	}
	
	public List<FareRouteLinkVO> getListByRouteId(Long routeId) {
		return transformList(findListByRouteId(routeId));
	}
	
	private List<FareRouteLink> findListByRouteId(Long routeId) {
		final TypedQuery<FareRouteLink> query = createNamedQuery(FareRouteLink.QUERY_FIND_ALL_BY_STATUS, getEntityClass())
				.setParameter(Route.PARAM_ROUTE_ID, routeId);
		return query.getResultList();
	}
}
