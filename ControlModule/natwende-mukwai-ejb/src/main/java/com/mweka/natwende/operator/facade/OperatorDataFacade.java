package com.mweka.natwende.operator.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.operator.entity.Operator;
import com.mweka.natwende.operator.vo.OperatorVO;
import com.mweka.natwende.types.OperatorName;
import com.mweka.natwende.types.Status;

@Stateless
public class OperatorDataFacade extends AbstractDataFacade<OperatorVO, Operator> {

	public OperatorDataFacade() {
		super(OperatorVO.class, Operator.class);
	}

	@Override
	protected void convertEntitytoVO(Operator entity, OperatorVO vo) {		
		convertBaseEntityToVO(entity, vo);		
		vo.setName(entity.getName());
	}

	@Override
	protected Operator convertVOToEntity(OperatorVO vo, Operator entity) {
		convertBaseVOToEntity(vo, entity);
		entity.setName(vo.getName());
		return entity;
	}

	@Override
	protected Operator updateEntity(OperatorVO vo) throws EntityNotFoundException {
		Operator entity = vo.getId() > 0 ? findById(vo.getId()) : new Operator();
		convertVOToEntity(vo, entity);
		update(entity);
		return entity;
	}
	
	public OperatorVO getById(Long id) {
		Operator entity = findById(id);
		return getCachedVO(entity);
	}
	
	public List<OperatorVO> getAllByStatus(Status status) {
		return transformList(findAllByStatus(status));
	}
	
	private List<Operator> findAllByStatus(Status status) {
		final TypedQuery<Operator> query = createNamedQuery(Operator.QUERY_FIND_ALL_BY_STATUS, getEntityClass())
				.setParameter(BaseEntity.PARAM_STATUS, status);
		return query.getResultList();
	}
	
	public boolean checkExistenceByName(OperatorName operatorName) {
		final TypedQuery<Integer> query = getEntityManager().createNamedQuery(Operator.QUERY_EXISTENCE_BY_NAME, Integer.class)
				.setParameter(Operator.PARAM_OPERATOR_NAME, operatorName);
		return query.getResultList().isEmpty(); // return true if operator is not enrolled on the database
	}

}