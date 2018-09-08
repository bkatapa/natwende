package com.mweka.natwende.operator.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.types.ConfigAttribute;

@Entity
@Table(name="OperatorSettings")
public class OperatorSettings extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotEmpty
	private String name;
	@ElementCollection
	@CollectionTable(name="ConfigAttributes")
	private List<ConfigAttribute> configAttributes;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<ConfigAttribute> getConfigAttributes() {
		return configAttributes;
	}
	
	public void setConfigAttributes(List<ConfigAttribute> configAttributes) {
		this.configAttributes = configAttributes;
	}	
}
