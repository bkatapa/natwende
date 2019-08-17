package com.mweka.natwende.operator.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.operator.entity.Operator;
import com.mweka.natwende.operator.entity.OperatorRouteLink;
import com.mweka.natwende.operator.vo.OperatorRouteLinkVO;
import com.mweka.natwende.route.entity.Route;

@Stateless
public class OperatorRouteLinkDataFacade extends AbstractDataFacade<OperatorRouteLinkVO, OperatorRouteLink> {

	public OperatorRouteLinkDataFacade() {
		super(OperatorRouteLinkVO.class, OperatorRouteLink.class);
	}

	@Override
	protected void convertEntitytoVO(OperatorRouteLink entity, OperatorRouteLinkVO vo) {		
		convertBaseEntityToVO(entity, vo);
		
		if (entity.getOperator() != null) {
			vo.setOperator(serviceLocator.getOperatorDataFacade().getCachedVO(entity.getOperator()));
		}
		if (entity.getRoute() != null) {
			vo.setRoute(serviceLocator.getRouteDataFacade().getCachedVO(entity.getRoute()));
		}
	}

	@Override
	protected OperatorRouteLink convertVOToEntity(OperatorRouteLinkVO vo, OperatorRouteLink entity) {
		convertBaseVOToEntity(vo, entity);
		
		if (vo.getOperator() != null) {
			entity.setOperator(serviceLocator.getOperatorDataFacade().findById(vo.getOperator().getId()));
		}
		if (vo.getRoute() != null) {
			entity.setRoute(serviceLocator.getRouteDataFacade().findById(vo.getRoute().getId()));
		}
		return entity;
	}

	@Override
	protected OperatorRouteLink updateEntity(OperatorRouteLinkVO vo) throws EntityNotFoundException {
		OperatorRouteLink entity = vo.getId() > 0 ? findById(vo.getId()) : new OperatorRouteLink();
		convertVOToEntity(vo, entity);
		update(entity);
		return entity;
	}
	
	public OperatorRouteLinkVO getByOperatorIdAndRouteId(Long operatorId, Long routeId) {
		List<OperatorRouteLink> resultList = createNamedQuery(OperatorRouteLink.QUERY_FIND_BY_OPERATOR_ID_AND_ROUTE_ID, getEntityClass())
				.setParameter(Operator.PARAM_OPERATOR_ID, operatorId)
				.setParameter(Route.PARAM_ROUTE_ID, routeId)
				.getResultList();
		return getVOFromList(resultList);
	}
	
	public List<OperatorRouteLinkVO> getListByOperatorId(Long operatorId) {
		return transformList(findListByOperatorId(operatorId));
	}
	
	private List<OperatorRouteLink> findListByOperatorId(Long operatorId) {
		final TypedQuery<OperatorRouteLink> query = createNamedQuery(OperatorRouteLink.QUERY_FIND_LIST_BY_OPERATOR_ID, getEntityClass())
				.setParameter(OperatorRouteLink.PARAM_OPERATOR_ID, operatorId);
		return query.getResultList();
	}
}
