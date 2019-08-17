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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((driver == null) ? 0 : driver.hashCode());
		result = prime * result + ((tripSchedule == null) ? 0 : tripSchedule.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusTripScheduleLinkVO other = (BusTripScheduleLinkVO) obj;
		if (bus == null) {
			return false;
		}
		if (other.bus == null) {
			return false;
		}
		if (tripSchedule == null) {
			return false;
		} 
		if (other.tripSchedule == null) {
			return false;
		} 
		if (tripSchedule.equals(other.tripSchedule) && bus.equals(other.bus)) {
			return true;
		}
		return false;
	}	
	
}
