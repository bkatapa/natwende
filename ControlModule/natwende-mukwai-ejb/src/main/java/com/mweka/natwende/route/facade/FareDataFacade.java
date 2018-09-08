package com.mweka.natwende.route.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.route.entity.Fare;
import com.mweka.natwende.route.entity.Route;
import com.mweka.natwende.route.vo.FareVO;

@Stateless
public class FareDataFacade extends AbstractDataFacade<FareVO, Fare> {

    public FareDataFacade() {
        super(FareVO.class, Fare.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected Fare updateEntity(FareVO vo) {
        Fare entity = vo.getId() > 0 ? findById(vo.getId()) : new Fare();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
    }

    @Override
    public void convertEntitytoVO(Fare entity, FareVO vo) {
    	convertBaseEntityToVO(entity, vo);    	
    	vo.setAmount(entity.getAmount());
    	
    	if (entity.getFrom() != null) {
    		vo.setFrom(serviceLocator.getStopDataFacade().getCachedVO(entity.getFrom()));
    	}    	
    	if (entity.getTo() != null) {
    		vo.setTo(serviceLocator.getStopDataFacade().getCachedVO(entity.getTo()));
    	}
    }

    @Override
    public Fare convertVOToEntity(FareVO vo, Fare entity) {
        convertBaseVOToEntity(vo, entity);
        entity.setAmount(vo.getAmount());
    	
    	if (vo.getFrom() != null) {
    		entity.setFrom(serviceLocator.getStopDataFacade().findById(vo.getFrom().getId()));
    	}    	
    	if (vo.getTo() != null) {
    		entity.setTo(serviceLocator.getStopDataFacade().findById(vo.getTo().getId()));
    	}
        return entity;
    }

    @Override
    public FareVO update(FareVO vo) {
        Fare entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    public List<FareVO> getListByRouteId(Long routeId) {
		return transformList(findListByRouteId(routeId));
	}
	
	private List<Fare> findListByRouteId(Long routeId) {
		final TypedQuery<Fare> query = createNamedQuery(Fare.QUERY_FIND_BY_ROUTE, getEntityClass())
				.setParameter(Route.PARAM_ROUTE_ID, routeId);
		return query.getResultList();
	}

}