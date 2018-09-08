package com.mweka.natwende.operator.facade;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.operator.vo.SeatVO;

@Stateless
@LocalBean
public class SeatFacade extends AbstractFacade<SeatVO> {

	public SeatFacade() {
		super(SeatVO.class);
	}
	
	public SeatVO saveSeat(SeatVO seat) throws Exception {
		try {
			return serviceLocator.getSeatDataFacade().update(seat);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}

	public void deleteSeat(Long seatId) throws Exception {
		try {
			serviceLocator.getSeatDataFacade().deleteById(seatId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public List<SeatVO> obtainSeatListByBus(Long busId) throws Exception {
		try {
			return serviceLocator.getSeatDataFacade().getAllByBusId(busId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public boolean doesSeatExist(String seatNo, String coordinates, Long busId) {
		SeatVO result = serviceLocator.getSeatDataFacade().getBySeatNoCoordinatesAndBusId(seatNo, coordinates, busId);
		return !(result == null); // seat exists!
	}
}
