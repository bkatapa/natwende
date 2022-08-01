package com.mweka.natwende.operator.facade;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang.StringUtils;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.operator.vo.BusVO;
import com.mweka.natwende.operator.vo.SeatVO;
import com.mweka.natwende.types.SeatClass;

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
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void deleteByList(List<SeatVO> seatList) throws Exception {
		try {
			for (SeatVO seat : seatList) {			
				serviceLocator.getSeatDataFacade().deleteById(seat.getId());
			}
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public void calibrateSeatCoordinates(BusVO bus) throws Exception {		
		List<SeatVO> resultList = serviceLocator.getSeatDataFacade().getAllByBusId(bus.getId());
		serviceLocator.getSeatFacade().deleteByList(resultList);
		String[] rows = bus.getSeatsAsString().split(",");
		int seatCount = 1;
		int rowNum = 1;
		for (String row : rows) {
			row = row.replace("'", StringUtils.EMPTY);
			char[] seats = row.toCharArray();
			int colNum = 1;
			for (char seat : seats) {
				if (seat != '_') {
					String coordinates = rowNum + "_" + colNum;
					SeatVO s = new SeatVO(bus, String.valueOf(seatCount++), coordinates);
					s.setSeatClass(SeatClass.STANDARD);
					serviceLocator.getSeatDataFacade().update(s);
				}
				colNum++;
			}
			rowNum++;
		}		
	}
 }
