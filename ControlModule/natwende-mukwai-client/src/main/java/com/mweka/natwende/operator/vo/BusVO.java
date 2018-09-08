package com.mweka.natwende.operator.vo;

import java.util.List;

import com.mweka.natwende.base.vo.BaseVO;

public class BusVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5870992347792401335L;
	
	private int capacity;
	private String reg;
	private String imgUrl;
	private OperatorVO operator;
	private List<SeatVO> seats;
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public String getReg() {
		return reg;
	}
	
	public void setReg(String reg) {
		this.reg = reg;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}
	
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public OperatorVO getOperator() {
		return operator;
	}

	public void setOperator(OperatorVO operator) {
		this.operator = operator;
	}

	public List<SeatVO> getSeats() {
		return seats;
	}
	
	public void setSeats(List<SeatVO> seats) {
		this.seats = seats;
	}
	
}
