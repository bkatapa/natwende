package com.mweka.natwende.operator.facade;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.operator.vo.BusVO;
import com.mweka.natwende.types.Status;

@Stateless
@LocalBean
public class BusFacade extends AbstractFacade<BusVO> {

	public BusFacade() {
		super(BusVO.class);
	}
	
	public void saveBus(BusVO bus) throws Exception {
		try {
			serviceLocator.getBusDataFacade().update(bus);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}

	public void deleteBus(Long busId) throws Exception {
		try {
			serviceLocator.getBusDataFacade().deleteById(busId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public List<BusVO> obtainBusListByOperatorAndStatus(Long operatorId, Status status) throws Exception {
		try {
			return serviceLocator.getBusDataFacade().getAllByOperatorIdAndStatus(operatorId, status);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
}
