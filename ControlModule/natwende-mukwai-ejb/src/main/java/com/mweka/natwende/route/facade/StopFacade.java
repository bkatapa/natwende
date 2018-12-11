package com.mweka.natwende.route.facade;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.route.vo.StopVO;
import com.mweka.natwende.types.Province;
import com.mweka.natwende.types.Status;
import com.mweka.natwende.types.Town;

@Stateless
@LocalBean
public class StopFacade extends AbstractFacade<StopVO> {

	public StopFacade() {
		super(StopVO.class);
	}
	
	public StopVO saveStop(StopVO stop) throws Exception {
		try {
			serviceLocator.getStopFacade().checkIfStationExistsByStationNameTownAndProvince(stop.getName(), stop.getTown(), stop.getProvince());
			return serviceLocator.getStopDataFacade().update(stop);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}

	public void deleteStop(Long stopId) throws Exception {
		try {
			serviceLocator.getStopDataFacade().deleteById(stopId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public StopVO obtainStopById(Long stopId) throws Exception {
		try {
			return serviceLocator.getStopDataFacade().getById(stopId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public List<StopVO> obtainStopByTown(Town town) throws Exception {
		try {
			return serviceLocator.getStopDataFacade().getByTown(town);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public List<StopVO> obtainAllByStatus(Status status) throws Exception {
		try {
			return serviceLocator.getStopDataFacade().getAllByStatus(status);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public void checkIfStationExistsByStationName(String stopName) throws Exception {
		StopVO result = serviceLocator.getStopDataFacade().getByName(stopName);
		if (result != null) {
			throw new Exception("Station name [" + stopName + "] already exists on the system.");
		}
	}
	
	public void checkIfStationExistsByStationNameTownAndProvince(String stopName, Town town, Province province) throws Exception {
		StopVO result = serviceLocator.getStopDataFacade().getByNameTownAndProvince(stopName, town, province);
		if (result != null) {
			throw new Exception("Station name [" + result + "] already exists on the system.");
		}
	}
}
