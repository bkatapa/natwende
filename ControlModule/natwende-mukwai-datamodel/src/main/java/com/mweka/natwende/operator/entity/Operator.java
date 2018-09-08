package com.mweka.natwende.operator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.types.OperatorName;

@Entity
@Table(name = "Operator",uniqueConstraints = {
    @UniqueConstraint(columnNames = {"operatorName"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Operator.QUERY_FIND_ALL, query = "SELECT o FROM Operator o"),
    @NamedQuery(name = Operator.QUERY_FIND_ALL_BY_STATUS, query = "SELECT o FROM Operator o WHERE o.status = :status"),
    @NamedQuery(name = Operator.QUERY_EXISTENCE_BY_NAME, query = "SELECT COUNT(o) FROM Operator o WHERE o.name = :operatorName")
})
public class Operator extends BaseEntity {

	/**
	 * 
	 */
	private static transient final long serialVersionUID = 5377300474860026591L;
	
	/**
	 * Named queries
	 */
	public static transient final String QUERY_FIND_ALL = "Operator.findAll";
	public static transient final String QUERY_FIND_ALL_BY_STATUS = "Operator.findAllByStatus";
	public static transient final String QUERY_EXISTENCE_BY_NAME = "Operator.queryExistenceByName";
	
	/**
	 * Query parameters
	 */
	public static transient final String PARAM_OPERATOR_ID = "operatorId";
	public static transient final String PARAM_OPERATOR_NAME = "operatorName";

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "operatorName")
	private OperatorName name;	

	public Operator(OperatorName name) {
		super();
		this.name = name;
	}

	public Operator() {
		super();
	}

	public OperatorName getName() {
		return name;
	}

	public void setName(OperatorName name) {
		this.name = name;
	}
}
