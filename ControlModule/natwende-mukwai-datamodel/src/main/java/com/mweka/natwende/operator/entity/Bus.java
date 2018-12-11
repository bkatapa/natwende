package com.mweka.natwende.operator.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;

@Entity
@Table(name = "Bus", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"reg"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Bus.QUERY_FIND_ALL, query="SELECT b FROM Bus b"),
    @NamedQuery(name = Bus.QUERY_FIND_ALL_BY_OPERATOR_ID_AND_STATUS, query = "SELECT b FROM Bus b WHERE b.operator.id = :operatorId AND b.status = :status")
})
public class Bus extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3260154196160752445L;
	
	/**
	 * Named queries
	 */
	public static final String QUERY_FIND_ALL = "Bus.findAll";
	public static final String QUERY_FIND_ALL_BY_OPERATOR_ID_AND_STATUS = "Bus.findAllByOperatorIdAndStatus";
	
	/**
	 * Query parameters
	 */
	public static final String PARAM_BUS_ID = "busId";
	public static final String PARAM_BUS_REG = "busReg";

	@NotNull
	private int capacity;
	
	@NotNull
	private String reg;
	
	private String imgUrl;
	
	@OneToMany(mappedBy = "bus", fetch = FetchType.LAZY)
	private List<Seat> seats;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "operator_id", referencedColumnName = "id")
	private Operator operator;
	
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
	
	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public List<Seat> getSeats() {
		return seats;
	}
	
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
}
