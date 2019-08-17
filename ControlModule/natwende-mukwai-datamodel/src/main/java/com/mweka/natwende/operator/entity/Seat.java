package com.mweka.natwende.operator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.types.SeatClass;

@Entity
@Table(name = "seat", uniqueConstraints = {
	@UniqueConstraint(columnNames = { "bus_id", "seat_no", "coordinates" })
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Seat.QUERY_FIND_ALL, query="SELECT s FROM Seat s"),
    @NamedQuery(name = Seat.QUERY_FIND_ALL_BY_BUS_ID, query = "SELECT s FROM Seat s WHERE s.bus.id = :busId"),
    @NamedQuery(name = Seat.QUERY_FIND_BY_SEATNO_AND_BUS_ID, query = "SELECT s FROM Seat s WHERE s.seatNo = :seatNo AND s.bus.id = :busId"),
    @NamedQuery(name = Seat.QUERY_FIND_BY_SEATNO_COORDINATES_AND_BUS_ID, query = "SELECT s FROM Seat s WHERE s.seatNo = :seatNo AND s.coordinates = :coordinates AND s.bus.id = :busId")
})
public class Seat extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4172710434208923954L;
	
	/**
	 * Named queries
	 */
	public static transient final String QUERY_FIND_ALL = "Seat.findAll";
	public static transient final String QUERY_FIND_ALL_BY_BUS_ID = "Seat.findAllByBusId";
	public static transient final String QUERY_FIND_BY_SEATNO_AND_BUS_ID = "Seat.findBySeatNoAndBusId";
	public static transient final String QUERY_FIND_BY_SEATNO_COORDINATES_AND_BUS_ID = "Seat.findBySeatNoCoordinatesAndBusId";
	
	/**
	 * Query parameters
	 */
	public static transient final String PARAM_SEAT_ID = "seatId";
	public static transient final String PARAM_SEAT_NO = "seatNo";
	public static transient final String PARAM_CO_ORDINATES = "coordinates";
	
	@NotNull
	private String coordinates;
	
	@NotNull
	@Column(name = "seat_no", length = 32)
	private String seatNo;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "seat_class", length = 32)
	private SeatClass seatClass;
	
	@JoinColumn(name = "bus_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Bus bus;
	
	public Bus getBus() {
		return bus;
	}
	
	public void setBus(Bus bus) {
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
	
}
