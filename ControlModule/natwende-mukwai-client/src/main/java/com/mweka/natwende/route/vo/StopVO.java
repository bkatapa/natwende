package com.mweka.natwende.route.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.types.Province;
import com.mweka.natwende.types.Town;

public class StopVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9214398275659753395L;
	
	private int index;
	
	@NotNull(message = "Station name cannot be empty.")
	@NotEmpty(message = "Station name cannot be empty.")
	private String name;
	
	@NotNull(message = "Town cannot be empty.")
	private Town town;
	
	@NotNull(message = "Province name cannot be empty.")
	private Province province;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Town getTown() {
		return town;
	}
	
	public void setTown(Town town) {
		this.town = town;
	}
	
	public Province getProvince() {
		return province;
	}
	
	public void setProvince(Province province) {
		this.province = province;
	}
	
	public String getDescription() {
		return toString();
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return this.town == null || this.province == null ? "" : this.name + ", "
				+ this.town.getDisplay() + ", "
				+ this.province.getDisplay();
	}
	
	public String getAsString() {
		return toString();
	}

}
