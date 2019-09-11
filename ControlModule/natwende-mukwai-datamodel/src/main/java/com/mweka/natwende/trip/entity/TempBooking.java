package com.mweka.natwende.trip.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.types.SeatStatus;

@Entity
@Table(name = "TempBooking")
@XmlRootElement
@NamedQueries({ 
	@NamedQuery(name = TempBooking.QUERY_FIND_ALL, query = " SELECT tb FROM TempBooking tb "),
	@NamedQuery(name = TempBooking.QUERY_FIND_BY_WS_SESSION, query = " SELECT tb FROM TempBooking tb WHERE tb.wsSessionId = :wsSessionId "),
	@NamedQuery(name = TempBooking.QUERY_FIND_BY_SEAT_STATUS, query = " SELECT tb FROM TempBooking tb WHERE tb.seatStatus = :seatStatus ")
})
public class TempBooking extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8416493207095149314L;

	@Column(name = "ws_session_id")
	private String wsSessionId;

	@Column(name = "trip_id")
	private Long tripId;

	@Column(name = "seat_no")
	private String seatNo;

	@Column(name = "seat_status")
	@Enumerated(EnumType.STRING)
	private SeatStatus seatStatus;

	@Column(name = "last_heart_beat")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastHeartBeat;

	public String getWsSessionId() {
		return wsSessionId;
	}

	public void setWsSessionId(String wsSessionId) {
		this.wsSessionId = wsSessionId;
	}

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public Date getLastHeartBeat() {
		return lastHeartBeat;
	}

	public void setLastHeartBeat(Date lastHeartBeat) {
		this.lastHeartBeat = lastHeartBeat;
	}

	public SeatStatus getSeatStatus() {
		return seatStatus;
	}

	public void setSeatStatus(SeatStatus seatStatus) {
		this.seatStatus = seatStatus;
	}

	// Named Queries
	public static transient final String QUERY_FIND_ALL = "TempBooking.findAll";
	public static transient final String QUERY_FIND_BY_WS_SESSION = "TempBooking.findBySeatStatus";
	public static transient final String QUERY_FIND_BY_SEAT_STATUS = "TempBooking.findBySeatStatus";

	// Query Parameters
	public static transient final String PARAM_TEMP_BOOKING_ID = "tempBookingId";
	public static transient final String PARAM_WS_SESSION_ID = "wsSessionId";
}
