package com.mweka.natwende.operator.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.operator.entity.Bus;
import com.mweka.natwende.operator.entity.Operator;
import com.mweka.natwende.operator.vo.BusVO;
import com.mweka.natwende.types.Status;

@Stateless
public class BusDataFacade extends AbstractDataFacade<BusVO, Bus> {

    public BusDataFacade() {
        super(BusVO.class, Bus.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected Bus updateEntity(BusVO vo) {
        Bus entity = vo.getId() > 0 ? findById(vo.getId()) : new Bus();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
    }

    @Override
    public void convertEntitytoVO(Bus entity, BusVO vo) {
    	super.convertBaseEntityToVO(entity, vo);
    	
    	vo.setCapacity(entity.getCapacity());
    	vo.setReg(entity.getReg());
    	vo.setImgUrl(entity.getImgUrl());
    	vo.setSeatsAsString(entity.getSeatsAsString());

    	if (entity.getOperator() != null) {
    		vo.setOperator(serviceLocator.getOperatorDataFacade().getCachedVO(entity.getOperator()));
    	}
    }

    @Override
    public Bus convertVOToEntity(BusVO vo, Bus entity) {
        super.convertBaseVOToEntity(vo, entity);
        
        entity.setCapacity(vo.getCapacity());
        entity.setReg(vo.getReg());
        entity.setImgUrl(vo.getImgUrl());
        entity.setSeatsAsString(vo.getSeatsAsString());
        
        if (vo.getOperator() != null) {
    		entity.setOperator(serviceLocator.getOperatorDataFacade().findById(vo.getOperator().getId()));
    	}
        return entity;
    }

    @Override
    public BusVO update(BusVO vo) {
        Bus entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    public List<BusVO> getAllByOperatorIdAndStatus(Long operatorId, Status status) {
		return transformList(findAllByOperatorIdAndStatus(operatorId, status));
	}
	
	private List<Bus> findAllByOperatorIdAndStatus(Long operatorId, Status status) {
		final TypedQuery<Bus> query = createNamedQuery(Bus.QUERY_FIND_ALL_BY_OPERATOR_ID_AND_STATUS, getEntityClass())
				.setParameter(Operator.PARAM_OPERATOR_ID, operatorId)
				.setParameter(Bus.PARAM_STATUS, status);
		return query.getResultList();
	}
	
	public List<BusVO> getUnScheduled(long operatorId) {
		List<Bus> resultList = createNamedQuery(Bus.QUERY_FIND_UNSCHEDULED_BY_OPERATOR_ID, getEntityClass())
				.setParameter(Operator.PARAM_OPERATOR_ID, operatorId)
				.getResultList();
		return transformList(resultList);
	}
	
	public BusVO getByReg(String busReg) {
		List<Bus> resultList = createNamedQuery(Bus.QUERY_FIND_BY_REG, getEntityClass())
				.setParameter(Bus.PARAM_BUS_REG, busReg.trim())
				.getResultList();
		return getVOFromList(resultList);
	}

}