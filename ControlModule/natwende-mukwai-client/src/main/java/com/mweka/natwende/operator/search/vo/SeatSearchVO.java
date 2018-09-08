package com.mweka.natwende.operator.search.vo;

import com.mweka.natwende.types.SeatClass;
import com.mweka.natwende.util.BaseSearchVO;

public class SeatSearchVO extends BaseSearchVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3266474332409798684L;
	
	private String seatNo;
    private String coordinates;
    private Long busId;
    private SeatClass seatClass;
    
    public SeatSearchVO() {}
    
    public SeatSearchVO(String seatNo, String coordinates, Long busId) {
    	this.seatNo = seatNo;
    	this.coordinates = coordinates;
    	this.busId = busId;
    }
    
    public SeatSearchVO(SeatClass seatClass) {
    	this.seatClass = seatClass;
    }

    public boolean hasFilters() {        
        if (seatNo == null || seatNo.trim().isEmpty()) {
            return false;
        }
        if (coordinates == null || coordinates.trim().isEmpty()) {
            return false;
        }
        if (busId == null || coordinates.trim().isEmpty()) {
            return false;
        }
        if (busId == null || busId < 1L) {
            return false;
        }
        if (seatClass == null) {
            return false;
        }
        return true;
    }

    public void clearSearch() {
        this.seatNo = null;
        this.coordinates = null;
        this.busId = null;
        this.seatClass = null;
    }

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public Long getBusId() {
		return busId;
	}

	public void setBusId(Long busId) {
		this.busId = busId;
	}

	public SeatClass getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(SeatClass seatClass) {
		this.seatClass = seatClass;
	}    

}
