package com.mweka.natwende.operator.vo;

import java.util.List;
import java.util.Scanner;

import com.mweka.natwende.base.vo.BaseVO;

public class BusVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5870992347792401335L;
	
	private int capacity;
	private String reg;
	private String imgUrl;
	private String seatsAsString;
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
	
	public String getSeatsAsString() {
		if (seatsAsString == null) {
			seatsAsString = "'e__ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee'";
		}
		return seatsAsString;
	}
	
	public void setSeatsAsString(String seatsAsString) {
		this.seatsAsString = seatsAsString;
	}
	
	public String getSeatsAsStringFormatted() {
		StringBuilder sb = new StringBuilder();
		String[] rows = getSeatsAsString().split(",");
		for (String row : rows) {
			sb.append(row.replaceAll("'", "")).append(System.lineSeparator());
		}
		return sb.toString();
	}
	
	public void setSeatsAsStringFormatted(String str) {
		StringBuilder sb = new StringBuilder();
		try (Scanner scan = new Scanner(str);) {
			scan.useDelimiter(System.lineSeparator());
			while (scan.hasNext()) {
				sb.append("'").append(scan.next()).append("'").append(",");
			}
		}
		seatsAsString = sb.deleteCharAt(sb.length() - 1).toString();
	}
}
