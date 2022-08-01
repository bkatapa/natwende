package com.mweka.natwende.trip.search.vo;

import java.util.Date;

import com.mweka.natwende.route.vo.StretchVO;
import com.mweka.natwende.trip.vo.TripVO;
import com.mweka.natwende.types.Town;
import com.mweka.natwende.util.BaseSearchVO;

public class TripSearchVO extends BaseSearchVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2873606669321380015L;
	private StretchVO stretch;
	private TripVO trip;
	private Date travelDate;
	private Date returnDate;
	protected Town fromTown;
	protected Town toTown;

	public TripSearchVO() {
	}
	
	public TripSearchVO(Town fromTown, Town toTown, Date travelDate) {
		this.fromTown = fromTown;
		this.toTown = toTown;
		this.travelDate = travelDate;
	}

	public boolean hasFilters() {
		if (fromTown != null && toTown != null) {
			return true;
		}	
		if (travelDate != null) {
			return true;
		}			
		return false;
	}

	public void clearSearch() {
		this.stretch = null;
		this.travelDate = null;
		this.returnDate = null;
		this.fromTown = null;
		this.toTown = null;
	}

	public StretchVO getStretch() {
		return stretch;
	}

	public void setStretch(StretchVO stretch) {
		this.stretch = stretch;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Town getFromTown() {
		return fromTown;
	}

	public void setFromTown(Town fromTown) {
		this.fromTown = fromTown;
	}

	public Town getToTown() {
		return toTown;
	}

	public void setToTown(Town toTown) {
		this.toTown = toTown;
	}

	public TripVO getTrip() {
		return trip;
	}

	public void setTrip(TripVO trip) {
		this.trip = trip;
	}
	
}
