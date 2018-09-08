package com.mweka.natwende.operator.vo;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.types.SeatClass;

public class SeatVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2485997896425285760L;
	
	private BusVO bus;
	private String coordinates;
	private String seatNo;
	private SeatClass seatClass;
	
	public SeatVO(String seatNo, String coordinates, SeatClass seatClass) {
		this.seatNo = seatNo;
		this.coordinates = coordinates;
		this.seatClass = seatClass;
	}
	
	public SeatVO(BusVO bus) {
		this.bus = bus;
	}
	
	public SeatVO() {
	}
	
	public BusVO getBus() {
		return bus;
	}
	
	public void setBus(BusVO bus) {
		this.bus = bus;
	}
	
	public String getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	
	public String getSeatNo() {
		return seatNo;
	}
	
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}
	
	public SeatClass getSeatClass() {
		return seatClass;
	}
	
	public void setSeatClass(SeatClass seatClass) {
		this.seatClass = seatClass;
	}
	
	@Override
    public String toString() {
        return "Seat No. [" + this.seatNo + "], Co-ordinates [" + this.coordinates + "], Cabin class [" + seatClass + "]";
    }

}
