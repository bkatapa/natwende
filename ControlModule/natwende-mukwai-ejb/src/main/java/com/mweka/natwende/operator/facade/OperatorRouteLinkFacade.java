package com.mweka.natwende.operator.facade;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.operator.vo.OperatorRouteLinkVO;
import com.mweka.natwende.operator.vo.SeatVO;

@Stateless
@LocalBean
public class OperatorRouteLinkFacade extends AbstractFacade<SeatVO> {

	public OperatorRouteLinkFacade() {
		super(OperatorRouteLinkVO.class);
	}

	public void deleteOperatorRouteLink(Long linkId) throws Exception {
		try {
			serviceLocator.getOperatorRouteLinkDataFacade().deleteById(linkId);
		} catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}

	public List<OperatorRouteLinkVO> obtainListByOperatorId(Long operatorId) throws Exception {
		try {
			return serviceLocator.getOperatorRouteLinkDataFacade().getListByOperatorId(operatorId);
		} catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}

}
