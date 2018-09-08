package com.mweka.natwende.operator.facade;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.operator.vo.OperatorVO;
import com.mweka.natwende.types.OperatorName;
import com.mweka.natwende.types.Status;

@Stateless
@LocalBean
public class OperatorFacade extends AbstractFacade<OperatorVO> {
	
	public OperatorFacade() {
		super(OperatorVO.class);
	}
	
	public OperatorVO saveOperator(OperatorVO operator) throws Exception {
		try {
			return serviceLocator.getOperatorDataFacade().update(operator);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public void autoEnrollOperator(OperatorName operatorName) throws Exception {
		boolean result = serviceLocator.getOperatorDataFacade().checkExistenceByName(operatorName);
		if (result) { // operator is not enrolled
			serviceLocator.getOperatorFacade().saveOperator(new OperatorVO(operatorName));
		}
	}
	
	public OperatorVO obtainOperatorById(Long operatorId) throws Exception {
		try {
			return serviceLocator.getOperatorDataFacade().getById(operatorId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}

	public void deleteOperator(Long operatorId) throws Exception {
		try {
			serviceLocator.getOperatorDataFacade().deleteById(operatorId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public List<OperatorVO> obtainOperatorListByStatus(Status status) throws Exception {
		try {
			return serviceLocator.getOperatorDataFacade().getAllByStatus(status);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
}
