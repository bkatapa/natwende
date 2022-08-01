package com.mweka.natwende.route.facade;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.route.vo.FareVO;

@Stateless
@LocalBean
public class FareFacade extends AbstractFacade<FareVO> {

	public FareFacade() {
		super(FareVO.class);
	}

	public void deleteFare(Long fareId) throws Exception {
		try {
			serviceLocator.getFareDataFacade().deleteById(fareId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public List<FareVO> obtainListByRouteId(Long routeId) throws Exception {
		try {
			return serviceLocator.getFareDataFacade().getListByRouteId(routeId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public List<FareVO> obtainListByRouteIdAndOperatorId(Long routeId, Long operatorId) throws Exception {
		try {
			return serviceLocator.getFareDataFacade().getListByRouteIdAndOperatorId(routeId, operatorId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public FareVO fetchtByOperatorIdAndStretchId(Long operatorId, Long stretchId) throws Exception {
		try {
			return serviceLocator.getFareDataFacade().getByOperatorIdAndStretchId(operatorId, stretchId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public BigDecimal fetchAvarageFare(long stretchId) throws Exception {
		try {
			List<FareVO> resultList = serviceLocator.getFareDataFacade().getFareListByStretchId(stretchId);
			BigDecimal sumFare = BigDecimal.ZERO;
			for (FareVO result : resultList) {
				sumFare = sumFare.add(result.getAmount());
			}
			return sumFare.compareTo(BigDecimal.ZERO) != 0 ? sumFare.divide(new BigDecimal(resultList.size())) : sumFare;
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
}
