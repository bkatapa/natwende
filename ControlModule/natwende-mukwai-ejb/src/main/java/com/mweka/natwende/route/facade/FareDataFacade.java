package com.mweka.natwende.route.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.operator.entity.Operator;
import com.mweka.natwende.route.entity.Fare;
import com.mweka.natwende.route.entity.Route;
import com.mweka.natwende.route.entity.Stretch;
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
    	
    	if (entity.getStretch() != null) {
    		vo.setStretch(serviceLocator.getStretchDataFacade().getCachedVO(entity.getStretch()));
    	}    	
    	if (entity.getOperator() != null) {
    		vo.setOperator(serviceLocator.getOperatorDataFacade().getCachedVO(entity.getOperator()));
    	}
    }

    @Override
    public Fare convertVOToEntity(FareVO vo, Fare entity) {
        convertBaseVOToEntity(vo, entity);
        entity.setAmount(vo.getAmount());
    	
    	if (vo.getStretch() != null) {
    		entity.setStretch(serviceLocator.getStretchDataFacade().findById(vo.getStretch().getId()));
    	}    	
    	if (vo.getOperator() != null) {
    		entity.setOperator(serviceLocator.getOperatorDataFacade().findById(vo.getOperator().getId()));
    	}
        return entity;
    }

    @Override
    public FareVO update(FareVO vo) {
        Fare entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    public FareVO getByOperatorIdAndStretchId(Long operatorId, Long stretchId) {
		List<Fare> resultList = createNamedQuery(Fare.QUERY_FIND_BY_OPERATOR_ID_AND_STRETCH_ID, getEntityClass())				
				.setParameter(Operator.PARAM_OPERATOR_ID, operatorId)
				.setParameter(Stretch.PARAM_STRETCH_ID, stretchId)
				.getResultList();
		return getVOFromList(resultList);
	}
    
    public List<FareVO> getListByRouteIdAndOperatorId(Long routeId, Long operatorId) {
		List<Fare> resultList = createNamedQuery(Fare.QUERY_FIND_BY_ROUTE_ID_AND_OPERATOR_ID, getEntityClass())
				.setParameter(Route.PARAM_ROUTE_ID, routeId)
				.setParameter(Operator.PARAM_OPERATOR_ID, operatorId)
				.getResultList();
		return transformList(resultList);
	}
    
    public List<FareVO> getListByRouteId(Long routeId) {
		return transformList(findListByRouteId(routeId));
	}
    
    public List<FareVO> getFareListByStretchId(long stretchId) {
    	List<Fare> resultList = createNamedQuery(Fare.QUERY_FIND_BY_STRETCH_ID, getEntityClass())
				.setParameter(Stretch.PARAM_STRETCH_ID, stretchId)
				.getResultList();
		return transformList(resultList);
    }
	
	private List<Fare> findListByRouteId(Long routeId) {
		final TypedQuery<Fare> query = createNamedQuery(Fare.QUERY_FIND_BY_ROUTE, getEntityClass())
				.setParameter(Route.PARAM_ROUTE_ID, routeId);
		return query.getResultList();
	}

}