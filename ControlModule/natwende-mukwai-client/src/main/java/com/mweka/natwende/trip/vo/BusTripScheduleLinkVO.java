package com.mweka.natwende.trip.vo;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.operator.vo.BusVO;
import com.mweka.natwende.user.vo.UserVO;

public class BusTripScheduleLinkVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2626951206490884463L;

	private BusVO bus;
	private TripScheduleVO tripSchedule;
	private UserVO driver;
	
	public BusTripScheduleLinkVO() {		
	}
	
	public BusTripScheduleLinkVO(BusVO bus, TripScheduleVO tripSchedule) {
		this.bus = bus;
		this.tripSchedule = tripSchedule;
	}
	
	public BusTripScheduleLinkVO(Long id, BusVO bus, TripScheduleVO tripSchedule) {
		setId(id);
		this.bus = bus;
		this.tripSchedule = tripSchedule;
	}
	
	public BusVO getBus() {
		return bus;
	}
	
	public void setBus(BusVO bus) {
		this.bus = bus;
	}
	
	public TripScheduleVO getTripSchedule() {
		return tripSchedule;
	}
	
	public void setTripSchedule(TripScheduleVO tripSchedule) {
		this.tripSchedule = tripSchedule;
	}
	
	public UserVO getDriver() {
		return driver;
	}
	
	public void setDriver(UserVO driver) {
		this.driver = driver;
	}	
	
}
